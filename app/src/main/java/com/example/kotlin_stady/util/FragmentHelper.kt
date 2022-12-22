package com.example.kotlin_stady.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class FragmentHelper private constructor() {

    lateinit var manager: FragmentManager


    companion object {
        val instance: FragmentHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            FragmentHelper()
        }
    }

    fun getManager(activity: FragmentActivity) {
        manager = activity.supportFragmentManager
    }

    fun replace(rootId: Int, fragment: Fragment) {
        manager.beginTransaction()
            .replace(rootId, fragment)
            .addToBackStack(fragment::class.java.simpleName)
            .commitAllowingStateLoss()
    }

    fun pop(){
        manager.popBackStack()
    }

}