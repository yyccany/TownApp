package com.example.townapp.security

import android.util.Log
import com.example.townapp.business.StructuredLogger
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object AuditLogger {

    private const val TAG = "TownApp.Audit"
    private const val MAX_LOG_ENTRIES = 1000

    private val auditLogs = mutableListOf<AuditEntry>()

    enum class ActionType {
        LOGIN, LOGOUT, DATA_ACCESS, DATA_MODIFICATION, SETTINGS_CHANGE, 
        PURCHASE, APP_START, APP_SHUTDOWN, DATABASE_OPERATION, 
        SECURITY_VIOLATION, PERMISSION_REQUEST
    }

    enum class Severity {
        INFO, WARNING, ERROR, CRITICAL
    }

    data class AuditEntry(
        val timestamp: String,
        val actionType: ActionType,
        val severity: Severity,
        val userId: String?,
        val description: String,
        val ipAddress: String? = null,
        val additionalInfo: Map<String, String> = emptyMap()
    ) {
        fun toLogString(): String {
            return buildString {
                append("[$timestamp] ")
                append("[${actionType.name}] ")
                append("[${severity.name}] ")
                userId?.let { append("[User:$it] ") }
                append(description)
                if (additionalInfo.isNotEmpty()) {
                    append(" | Details: ${additionalInfo.entries.joinToString { "${it.key}=${it.value}" }}")
                }
            }
        }
    }

    fun logAction(
        actionType: ActionType,
        severity: Severity,
        description: String,
        userId: String? = null,
        additionalInfo: Map<String, String> = emptyMap()
    ) {
        val entry = AuditEntry(
            timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            actionType = actionType,
            severity = severity,
            userId = userId,
            description = description,
            additionalInfo = additionalInfo
        )

        synchronized(auditLogs) {
            auditLogs.add(0, entry)
            if (auditLogs.size > MAX_LOG_ENTRIES) {
                auditLogs.removeLast()
            }
        }

        when (severity) {
            Severity.INFO -> Log.i(TAG, entry.toLogString())
            Severity.WARNING -> Log.w(TAG, entry.toLogString())
            Severity.ERROR -> Log.e(TAG, entry.toLogString())
            Severity.CRITICAL -> Log.wtf(TAG, entry.toLogString())
        }

        StructuredLogger.logSecurity(TAG, actionType.name, severity.name, description)
    }

    fun logLogin(userId: String) {
        logAction(
            actionType = ActionType.LOGIN,
            severity = Severity.INFO,
            description = "User logged in",
            userId = userId
        )
    }

    fun logLogout(userId: String) {
        logAction(
            actionType = ActionType.LOGOUT,
            severity = Severity.INFO,
            description = "User logged out",
            userId = userId
        )
    }

    fun logDataAccess(userId: String?, dataType: String, recordId: String?) {
        logAction(
            actionType = ActionType.DATA_ACCESS,
            severity = Severity.INFO,
            description = "Accessing $dataType data",
            userId = userId,
            additionalInfo = if (recordId != null) mapOf("recordId" to recordId) else emptyMap()
        )
    }

    fun logDataModification(userId: String?, dataType: String, operation: String, recordId: String?) {
        logAction(
            actionType = ActionType.DATA_MODIFICATION,
            severity = Severity.INFO,
            description = "$operation $dataType data",
            userId = userId,
            additionalInfo = buildMap {
                put("operation", operation)
                recordId?.let { put("recordId", it) }
            }
        )
    }

    fun logSettingsChange(userId: String?, settingKey: String, oldValue: String, newValue: String) {
        logAction(
            actionType = ActionType.SETTINGS_CHANGE,
            severity = Severity.INFO,
            description = "Settings changed: $settingKey",
            userId = userId,
            additionalInfo = mapOf(
                "settingKey" to settingKey,
                "oldValue" to oldValue,
                "newValue" to newValue
            )
        )
    }

    fun logSecurityViolation(userId: String?, description: String) {
        logAction(
            actionType = ActionType.SECURITY_VIOLATION,
            severity = Severity.CRITICAL,
            description = "Security violation: $description",
            userId = userId
        )
    }

    fun logDatabaseOperation(operation: String, tableName: String, success: Boolean) {
        logAction(
            actionType = ActionType.DATABASE_OPERATION,
            severity = if (success) Severity.INFO else Severity.ERROR,
            description = "Database operation: $operation on $tableName",
            additionalInfo = mapOf("success" to success.toString())
        )
    }

    fun getRecentLogs(count: Int = 50): List<AuditEntry> {
        return synchronized(auditLogs) {
            auditLogs.take(count)
        }
    }

    fun clearLogs() {
        synchronized(auditLogs) {
            auditLogs.clear()
        }
        Log.i(TAG, "Audit logs cleared")
    }
}