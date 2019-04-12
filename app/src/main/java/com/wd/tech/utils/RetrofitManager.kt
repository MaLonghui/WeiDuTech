


package com.wd.tech.utils
import com.wd.tech.api.Api
import com.wd.tech.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager {
        private var okclient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .readTimeout(3000,TimeUnit.SECONDS)
                .writeTimeout(3000,TimeUnit.SECONDS)
                .callTimeout(3000,TimeUnit.SECONDS)
                .build()
        private var retrofit = Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(okclient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    fun <T: ApiService> creat(service:Class<T>): T {
        return retrofit!!.create(service)
    }


    companion object {

        val INSTANCE by lazy { RetrofitManager() }
    }

}