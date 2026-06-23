package com.example.townapp.security

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

import android.os.Build

object SignatureValidator {

    private const val TAG = "SignatureValidator"

    fun validateAppIntegrity(context: Context): Boolean {
        return try {
            val packageInfo = getPackageInfo(context)
            val signatures = getSignatures(packageInfo)
            validateSignatures(signatures)
            true
        } catch (e: Exception) {
            Log.e(TAG, "应用完整性校验失败", e)
            false
        }
    }

    private fun getPackageInfo(context: Context): PackageInfo {
        val packageName = context.packageName
        return context.packageManager.getPackageInfo(
            packageName,
            PackageManager.GET_SIGNING_CERTIFICATES
        )
    }

    @Suppress("DEPRECATION")
    private fun getSignatures(packageInfo: PackageInfo): Array<android.content.pm.Signature> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.signingInfo?.apkContentsSigners ?: emptyArray()
        } else {
            packageInfo.signatures ?: emptyArray()
        }
    }

    private fun validateSignatures(signatures: Array<android.content.pm.Signature>) {
        if (signatures.isEmpty()) {
            throw SecurityException("未找到应用签名")
        }

        signatures.forEach { signature ->
            val hash = getSignatureHash(signature)
            Log.d(TAG, "签名哈希: $hash")
            
            validateSignatureHash(hash)
        }
    }

    private fun getSignatureHash(signature: android.content.pm.Signature): String {
        val signatureBytes = signature.toByteArray()
        return try {
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(signatureBytes)
            digest.joinToString("") { "%02x".format(it) }
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("SHA-256算法不可用", e)
        }
    }

    private fun validateSignatureHash(hash: String) {
        val allowedHashes = getAllowedSignatureHashes()
        if (allowedHashes.isEmpty()) {
            Log.w(TAG, "未配置允许的签名哈希，跳过严格校验")
            return
        }

        if (hash !in allowedHashes) {
            throw SecurityException("无效的应用签名: $hash")
        }
    }

    private fun getAllowedSignatureHashes(): Set<String> {
        return emptySet()
    }

    fun getCurrentSignatureHash(context: Context): String? {
        return try {
            val packageInfo = getPackageInfo(context)
            val signatures = getSignatures(packageInfo)
            val signature = signatures.firstOrNull()
            signature?.let { getSignatureHash(it) }
        } catch (e: Exception) {
            Log.e(TAG, "获取签名哈希失败", e)
            null
        }
    }

    fun checkForTampering(context: Context): Boolean {
        return try {
            val packageInfo = getPackageInfo(context)
            val signatures = getSignatures(packageInfo)
            
            if (signatures.isEmpty()) {
                Log.w(TAG, "检测到无签名APK")
                return true
            }

            val expectedCount = 1
            if (signatures.size != expectedCount) {
                Log.w(TAG, "检测到异常签名数量: ${signatures.size}")
                return true
            }

            false
        } catch (e: Exception) {
            Log.e(TAG, "篡改检测失败", e)
            true
        }
    }
}