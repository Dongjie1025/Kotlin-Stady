package com.example.kotlin_stady.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtil{

    private val TAG = NetworkUtil::class.java.simpleName
    private const val TYPE_NO_CONNECT = -1
    private const val TYPE_WIFI = 1
    private const val TYPE_MOBILE = 2

    fun isNetworkConnect(context: Context): Boolean {
        return getNetworkType(context) != TYPE_NO_CONNECT
    }

    fun isWifiConnect(context: Context): Boolean {
        return getNetworkType(context) == TYPE_WIFI
    }

    fun isMobileConnect(context: Context): Boolean {
        return getNetworkType(context) == TYPE_MOBILE
    }

    private fun getNetworkType(context: Context): Int {
        var type = TYPE_NO_CONNECT
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if (null == networkCapabilities) {
            LogUtil.d(TAG, "network not connect ~ ")
        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            type = TYPE_WIFI
        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            type = TYPE_MOBILE
        }
        return type
    }
}



