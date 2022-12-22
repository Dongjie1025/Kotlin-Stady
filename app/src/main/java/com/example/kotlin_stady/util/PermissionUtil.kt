package com.example.kotlin_stady.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionUtil private constructor() {

    private val REQUEST_PERMISSION_CODE = 1

    companion object {
        val instance: PermissionUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PermissionUtil()
        }
    }

    /**
     * 检查单个权限
     */
    fun checkSinglePermission(activity: Activity, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 检查多个权限并且返回未授权的权限List
     */
    fun checkMultiplePermission(activity: Activity, permissions: Array<String>): List<String> {
        var deniedPermissionList = ArrayList<String>()
        permissions.forEach {
            if (!checkSinglePermission(activity, it)) {
                deniedPermissionList.add(it)
            }
        }
        return deniedPermissionList
    }

    /**
     *  请求一个权限
     */
    fun requestPermission(activity: Activity, permission: String) {
        if (!checkSinglePermission(activity, permission)) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission),
                REQUEST_PERMISSION_CODE
            )
        }
    }

    /**
     * 请求多个权限
     */
    fun requestMultiplePermission(activity: Activity, permissions: Array<String>) {
        for (deniedPermission in checkMultiplePermission(activity, permissions)) {
            requestPermission(activity, deniedPermission)
        }
    }


}