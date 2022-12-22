package com.example.kotlin_stady.api

import com.example.kotlin_stady.bean.BaseResponse
import com.example.kotlin_stady.bean.MsgCountBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface IMainApi {

    @GET("https://ifis-cloud.jddglobal.com/customer/app/message/getMessageUnReadCount")
    fun readSystemAll() : Observable<MsgCountBean>
}