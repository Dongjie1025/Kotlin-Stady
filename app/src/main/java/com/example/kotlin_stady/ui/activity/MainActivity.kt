package com.example.kotlin_stady.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.kotlin_stady.R
import com.example.kotlin_stady.api.IMainApi
import com.example.kotlin_stady.base.BaseActivity
import com.example.kotlin_stady.bean.BaseResponse
import com.example.kotlin_stady.bean.MsgCountBean
import com.example.kotlin_stady.learn.LearnRxjava
import com.example.kotlin_stady.learn.S
import com.example.kotlin_stady.network.NetFactory
import com.example.kotlin_stady.util.LogUtil
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.function.Consumer

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initListener()
        initData()
    }


    private fun initView() {
    }

    private fun initListener() {
    }

    private fun initData() {
        NetFactory.retrofit.create(IMainApi::class.java)
            .readSystemAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<MsgCountBean> {
                override fun onNext(t: MsgCountBean) {
                    LogUtil.d("SystemNotice :: ${t.SystemNotice}   + code :: ${t.code}")
                }

                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    LogUtil.d(e.toString())
                }

            })
    }
}