package com.example.townapp.business

import android.util.Log
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * 结构化日志系统
 * 提供比普通 Log 更加结构化的日志输出，便于日志分析和监控
 */
object StructuredLogger {
    
    private const val TAG = "TownApp"
    
    // 日期格式器（线程安全）
    private val dateFormat = ThreadLocal.withInitial {
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
    }
    
    /**
     * 日志级别
     */
    enum class Level(val priority: Int) {
        VERBOSE(2),
        DEBUG(3),
        INFO(4),
        WARN(5),
        ERROR(6),
        CRITICAL(7)
    }
    
    /**
     * 日志上下文
     */
    data class LogContext(
        val tag: String,
        val level: Level,
        val message: String,
        val data: Map<String, Any>? = null,
        val error: Throwable? = null,
        val timestamp: Long = System.currentTimeMillis()
    ) {
        fun toJsonString(): String {
            val json = JSONObject().apply {
                put("timestamp", dateFormat.get()!!.format(Date(timestamp)))
                put("level", level.name)
                put("tag", tag)
                put("message", message)
                data?.let { put("data", JSONObject(it)) }
                error?.let {
                    put("error", JSONObject().apply {
                        put("type", it.javaClass.simpleName)
                        put("message", it.message)
                        put("stackTrace", it.stackTraceToString().take(500))
                    })
                }
            }
            return json.toString()
        }
        
        fun toFormattedString(): String {
            return buildString {
                append("[${dateFormat.get()!!.format(Date(timestamp))}] ")
                append("[${level.name.padEnd(8)}] ")
                append("[$tag] ")
                append(message)
                data?.let {
                    append("\n  Data: ${it.entries.joinToString(", ") { (k, v) -> "$k=$v" }}")
                }
                error?.let {
                    append("\n  Error: ${it.javaClass.simpleName}: ${it.message}")
                }
            }
        }
    }
    
    /**
     * 日志监听器接口
     */
    interface LogListener {
        fun onLog(context: LogContext)
    }
    
    private val listeners = mutableListOf<LogListener>()
    private var minLevel = Level.DEBUG
    
    /**
     * 注册日志监听器
     */
    fun addListener(listener: LogListener) {
        listeners.add(listener)
    }
    
    /**
     * 移除日志监听器
     */
    fun removeListener(listener: LogListener) {
        listeners.remove(listener)
    }
    
    /**
     * 设置最小日志级别
     */
    fun setMinLevel(level: Level) {
        minLevel = level
    }
    
    /**
     * 创建日志上下文
     */
    private fun createContext(
        tag: String,
        level: Level,
        message: String,
        data: Map<String, Any>? = null,
        error: Throwable? = null
    ): LogContext {
        return LogContext(tag, level, message, data, error)
    }
    
    /**
     * 记录日志
     */
    private fun log(context: LogContext) {
        if (context.level.priority < minLevel.priority) {
            return
        }
        
        // 输出到 Android Log
        val logMessage = context.toFormattedString()
        when (context.level) {
            Level.VERBOSE -> Log.v(context.tag, logMessage, context.error)
            Level.DEBUG -> Log.d(context.tag, logMessage, context.error)
            Level.INFO -> Log.i(context.tag, logMessage, context.error)
            Level.WARN -> Log.w(context.tag, logMessage, context.error)
            Level.ERROR -> Log.e(context.tag, logMessage, context.error)
            Level.CRITICAL -> Log.wtf(context.tag, logMessage, context.error)
        }
        
        // 通知所有监听器
        listeners.forEach { it.onLog(context) }
    }
    
    // ==================== 便捷方法 ====================
    
    /**
     * 调试日志
     */
    fun d(tag: String, message: String, data: Map<String, Any>? = null) {
        log(createContext(tag, Level.DEBUG, message, data))
    }
    
    /**
     * 信息日志
     */
    fun i(tag: String, message: String, data: Map<String, Any>? = null) {
        log(createContext(tag, Level.INFO, message, data))
    }
    
    /**
     * 警告日志
     */
    fun w(tag: String, message: String, error: Throwable? = null) {
        log(createContext(tag, Level.WARN, message, error = error))
    }
    
    /**
     * 错误日志
     */
    fun e(tag: String, message: String, error: Throwable? = null, data: Map<String, Any>? = null) {
        log(createContext(tag, Level.ERROR, message, data, error))
    }
    
    /**
     * 严重错误日志
     */
    fun wtf(tag: String, message: String, error: Throwable? = null) {
        log(createContext(tag, Level.CRITICAL, message, error = error))
    }
    
    // ==================== 性能计时便捷方法 ====================
    
    /**
     * 开始计时（便捷方法）
     */
    fun start(operation: String): Long {
        return System.currentTimeMillis()
    }
    
    /**
     * 结束计时（便捷方法）
     */
    fun end(operation: String, startTime: Long): Long {
        return System.currentTimeMillis() - startTime
    }
    
    // ==================== 业务日志方法 ====================
    
    /**
     * 数据库操作日志
     */
    fun logDatabase(tag: String, operation: String, duration: Long, success: Boolean, error: Throwable? = null) {
        val data = mapOf(
            "operation" to operation,
            "duration" to duration,
            "success" to success
        )
        if (success) {
            i(tag, "数据库操作完成", data)
        } else {
            e(tag, "数据库操作失败", error, data)
        }
    }
    
    /**
     * UI 事件日志
     */
    fun logUI(tag: String, event: String, data: Map<String, Any>? = null) {
        d(tag, "UI事件: $event", data)
    }
    
    /**
     * 性能日志
     */
    fun logPerformance(tag: String, operation: String, duration: Long, threshold: Long = 100) {
        val data = mapOf(
            "operation" to operation,
            "duration" to duration,
            "threshold" to threshold,
            "exceeded" to (duration > threshold)
        )
        if (duration > threshold) {
            w(tag, "性能警告: $operation 耗时 ${duration}ms 超过阈值 ${threshold}ms", null)
        } else {
            d(tag, "性能: $operation 耗时 ${duration}ms", data)
        }
    }
    
    /**
     * 用户行为日志
     */
    fun logUserAction(tag: String, action: String, context: Map<String, Any>? = null) {
        i(tag, "用户行为: $action", context)
    }
    
    /**
     * 业务数据变更日志
     */
    fun logDataChange(tag: String, entity: String, id: Long, operation: String, before: Any? = null, after: Any? = null) {
        val data = mapOf(
            "entity" to entity,
            "id" to id,
            "operation" to operation,
            "before" to (before?.toString() ?: "null"),
            "after" to (after?.toString() ?: "null")
        )
        i(tag, "数据变更: $entity #$id $operation", data)
    }
    
    /**
     * 安全日志
     */
    fun logSecurity(tag: String, actionType: String, severity: String, description: String) {
        val data = mapOf(
            "actionType" to actionType,
            "severity" to severity,
            "description" to description
        )
        when (severity) {
            "INFO" -> i(tag, "安全事件: $actionType", data)
            "WARNING" -> w(tag, "安全警告: $actionType - $description", null)
            "ERROR", "CRITICAL" -> e(tag, "安全错误: $actionType - $description", null, data)
            else -> i(tag, "安全事件: $actionType", data)
        }
    }
}

/**
 * 性能监控器
 */
object PerformanceMonitor {
    
    private val measurements = mutableMapOf<String, MutableList<Long>>()
    private const val MAX_SAMPLES = 100
    
    /**
     * 开始计时
     */
    fun start(operation: String): Long {
        return System.currentTimeMillis()
    }
    
    /**
     * 结束计时并记录
     */
    fun end(operation: String, startTime: Long) {
        val duration = System.currentTimeMillis() - startTime
        record(operation, duration)
    }
    
    /**
     * 记录测量值
     */
    private fun record(operation: String, duration: Long) {
        val samples = measurements.getOrPut(operation) { mutableListOf() }
        samples.add(duration)
        if (samples.size > MAX_SAMPLES) {
            samples.removeAt(0)
        }
        
        // 记录到日志
        StructuredLogger.logPerformance("Performance", operation, duration)
    }
    
    /**
     * 获取统计数据
     */
    fun getStats(operation: String): PerformanceStats? {
        val samples = measurements[operation] ?: return null
        if (samples.isEmpty()) return null
        
        val sorted = samples.sorted()
        return PerformanceStats(
            operation = operation,
            count = samples.size,
            min = sorted.first(),
            max = sorted.last(),
            avg = samples.average(),
            p50 = sorted[sorted.size / 2],
            p90 = sorted[(sorted.size * 0.9).toInt().coerceAtMost(sorted.size - 1)],
            p99 = sorted[(sorted.size * 0.99).toInt().coerceAtMost(sorted.size - 1)]
        )
    }
    
    /**
     * 获取所有统计
     */
    fun getAllStats(): List<PerformanceStats> {
        return measurements.keys.mapNotNull { getStats(it) }
    }
    
    /**
     * 清除所有测量
     */
    fun clear() {
        measurements.clear()
    }
}

/**
 * 性能统计数据
 */
data class PerformanceStats(
    val operation: String,
    val count: Int,
    val min: Long,
    val max: Long,
    val avg: Double,
    val p50: Long,
    val p90: Long,
    val p99: Long
) {
    override fun toString(): String {
        return buildString {
            append("[$operation] ")
            append("count=$count, ")
            append("min=${min}ms, ")
            append("avg=${String.format("%.2f", avg)}ms, ")
            append("p90=${p90}ms, ")
            append("max=${max}ms")
        }
    }
}
