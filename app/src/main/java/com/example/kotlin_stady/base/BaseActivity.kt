package com.example.kotlin_stady.base

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.WindowMetrics
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_stady.util.LogUtil

abstract class BaseActivity : AppCompatActivity() {

    private val twiceInterval = 1000// 有效点击的间隔时间
    private var lastClickTime = 0L// 记录上次点击的时间

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    /**
     * 检测是否暴力点击
     */
    fun fastClick(): Boolean {
        if (System.currentTimeMillis() - lastClickTime < twiceInterval) {
            return true
        }
        lastClickTime = System.currentTimeMillis()
        return false
    }

}
