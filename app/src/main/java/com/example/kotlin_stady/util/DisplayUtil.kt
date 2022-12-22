package com.example.kotlin_stady.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Window
import android.view.WindowMetrics

/**
 * 屏幕单位转换
 */
class DisplayUtil private constructor() {

    companion object {
        val instance: DisplayUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { DisplayUtil() }
    }

    /**
     *  获取屏幕高度
     */
    fun getWindowHeight(activity: Activity): Int {
        val dm: DisplayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }

    /**
     * 获取屏幕宽度
     */
    fun getWindowWidth(activity: Activity): Int {
        val dm: DisplayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    /**
     * dp转化px单位
     */
    fun dpToPx(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    /**
     * px转化dp单位
     */
    fun pxToDp(context: Context, px: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    fun pxToDp(context: Context, px: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            px.toFloat(), context.resources.displayMetrics
        ).toInt()
    }



}