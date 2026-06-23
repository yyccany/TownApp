package com.example.townapp.feature.town_simulation

import android.util.Log

/**
 * 应用异常处理中心
 * 统一管理所有业务异常的捕获、记录和恢复
 */
object ExceptionHandler {
    
    private const val TAG = "TownApp.Exception"
    
    /**
     * 异常级别枚举
     */
    enum class Level {
        DEBUG,      // 调试信息
        INFO,       // 一般信息
        WARNING,    // 警告（可恢复）
        ERROR,      // 错误（需处理）
        CRITICAL    // 严重错误（应用可能崩溃）
    }
    
    /**
     * 异常上下文数据类
     */
    data class ExceptionContext(
        val tag: String,
        val level: Level,
        val message: String,
        val exception: Throwable?,
        val timestamp: Long = System.currentTimeMillis()
    ) {
        fun toLogString(): String {
            return buildString {
                append("[${timestamp}] ")
                append("[$level] ")
                append("[$tag] ")
                append(message)
                exception?.let {
                    append("\nException: ${it.javaClass.simpleName}")
                    append("\nMessage: ${it.message}")
                }
            }
        }
    }
    
    /**
     * 全局异常处理器
     */
    interface GlobalExceptionHandler {
        fun onException(context: ExceptionContext)
    }
    
    private val handlers = mutableListOf<GlobalExceptionHandler>()
    
    /**
     * 注册全局异常处理器
     */
    fun registerHandler(handler: GlobalExceptionHandler) {
        handlers.add(handler)
    }
    
    /**
     * 统一异常处理入口
     */
    fun handle(
        tag: String,
        level: Level,
        message: String,
        exception: Throwable? = null
    ) {
        val context = ExceptionContext(tag, level, message, exception)
        
        // 输出到日志
        when (level) {
            Level.DEBUG -> Log.d(tag, message, exception)
            Level.INFO -> Log.i(tag, message, exception)
            Level.WARNING -> Log.w(tag, message, exception)
            Level.ERROR -> Log.e(tag, message, exception)
            Level.CRITICAL -> Log.wtf(tag, message, exception)
        }
        
        // 通知所有处理器
        handlers.forEach { it.onException(context) }
    }
    
    /**
     * 便捷方法
     */
    fun debug(tag: String, message: String) = handle(tag, Level.DEBUG, message)
    fun info(tag: String, message: String) = handle(tag, Level.INFO, message)
    fun warning(tag: String, message: String, ex: Throwable? = null) = handle(tag, Level.WARNING, message, ex)
    fun error(tag: String, message: String, ex: Throwable? = null) = handle(tag, Level.ERROR, message, ex)
    fun critical(tag: String, message: String, ex: Throwable? = null) = handle(tag, Level.CRITICAL, message, ex)
    
    /**
     * 数据库操作异常处理
     */
    fun handleDatabaseException(tag: String, operation: String, ex: Throwable): DatabaseException {
        error(tag, "数据库操作失败: $operation", ex)
        return DatabaseException(operation, ex)
    }
    
    /**
     * 业务逻辑异常处理
     */
    fun handleBusinessException(tag: String, message: String, ex: Throwable? = null): BusinessException {
        warning(tag, "业务逻辑异常: $message", ex)
        return BusinessException(message, ex)
    }
    
    /**
     * UI渲染异常处理
     */
    fun handleUIException(tag: String, message: String, ex: Throwable): UIException {
        error(tag, "UI渲染异常: $message", ex)
        return UIException(message, ex)
    }
}

/**
 * 数据库异常
 */
class DatabaseException(
    val operation: String,
    cause: Throwable? = null
) : Exception("数据库操作失败: $operation", cause)

/**
 * 业务逻辑异常
 */
class BusinessException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)

/**
 * UI渲染异常
 */
class UIException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)
