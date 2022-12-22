package com.example.kotlin_stady.network

import com.example.kotlin_stady.api.IHostApi
import com.example.kotlin_stady.base.InitApp
import com.example.kotlin_stady.util.NetworkUtil
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.math.max

class NetFactory {

    companion object {
        private final val cacheInterceptor: Interceptor = Interceptor {
            var request = it.request()
            if (!NetworkUtil.isNetworkConnect(InitApp.getAppContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }
            val response: Response = it.proceed(request)
            response.newBuilder().header("token","6728563be95d7722691513ea218e47f3")
                .header("User-Agent","Android-1.1.22").build()

            val cacheStr = request.cacheControl().toString()
            if (NetworkUtil.isNetworkConnect(InitApp.getAppContext())) {
                return@Interceptor response.newBuilder()
                    .header("Cache-Control", cacheStr)
                    .removeHeader("Pragma")
                    .build()
            } else {
                val maxStale = 60 * 60 * 24 * 7
                return@Interceptor response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale= $maxStale")
                    .removeHeader("Pragma")
                    .build()
            }

        }

        val retrofit by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            val cache = Cache(File(InitApp.getAppContext().cacheDir,"HttpCache"),1024 * 1024 * 50)
            val okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(cacheInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
            Retrofit.Builder()
                .baseUrl(IHostApi.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }

}