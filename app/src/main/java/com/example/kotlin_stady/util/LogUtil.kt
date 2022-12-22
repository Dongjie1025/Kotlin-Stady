package com.example.kotlin_stady.util

import android.text.TextUtils
import android.util.Log

/**
 * 打印日志工具类
 */
object LogUtil {

    private val TAG = LogUtil::class.java.simpleName
    private const val maxLineLength = 200

    private var isPrintLog = true

    /**
     * 是否打印日志
     */
    fun isPrintLog(isPrint: Boolean) {
        isPrintLog = isPrint
    }

    /**
     * 使用默认TAG打印日志
     */
    fun d(msg: String) {
        d(TAG, msg)
    }

    /**
     * 使用自定义格式打印日志
     */
    fun d(tag: String, msg: String) {
        if (!isPrintLog or TextUtils.isEmpty(msg)) {
            Log.d(TAG, "isPrintLog is false or msg is empty ~")
            return
        }
        val msgLength = msg.length
        var start = 0
        var end = maxLineLength
        val sbf = StringBuffer()
        var lineMsg = ""
        if (msgLength < maxLineLength) {
            sbf.append(msg)
        } else {
            while (end < msgLength) {
                if (msgLength - end > maxLineLength) {
                    lineMsg = msg.substring(start, end)
                } else {
                    lineMsg = msg.substring(start, msgLength)
                }
                start += end
                end += maxLineLength
                sbf.append(lineMsg + "\n")
            }
        }
        Log.d(tag, sbf.toString())
    }

}