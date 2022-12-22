package com.example.kotlin_stady.util

import com.example.kotlin_stady.base.InitApp

class SpUtil private constructor() {
    companion object {
        val instance: SpUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SpUtil()
        }
    }

    private val context = InitApp().baseContext

    fun put(key: String?, obj: Any) {

    }
}