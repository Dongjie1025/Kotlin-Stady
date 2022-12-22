package com.example.kotlin_stady.base

import android.app.Application
import android.content.Context
import com.example.kotlin_stady.BuildConfig
import com.example.kotlin_stady.util.LogUtil

class InitApp : Application() {

    companion object {
        private var instance: InitApp? = null
        fun getAppContext(): Context {
            return instance!!.baseContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        actionBarConfig()
        systemConfig()
    }

    private fun actionBarConfig() {
    }

    private fun systemConfig() {
        if (BuildConfig.DEBUG) {
            // 打印Log日志
            LogUtil.isPrintLog(true)
        }
    }
}