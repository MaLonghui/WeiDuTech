package com.wd.tech.mvp

import com.google.gson.Gson
import com.wd.tech.api.ApiService
import com.wd.tech.base.BasePresenter
import com.wd.tech.utils.RetrofitManager
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers
import java.io.File


class Presenter : BasePresenter<Constanct.View>(), Constanct.Presenter {
    override fun imgsPostPresenter(uri: String, headerMap: Map<String, Any>, parms: Map<String, Any>, file: File, clazz: Class<*>) {
        val apiService = RetrofitManager.INSTANCE.creat(ApiService::class.java)
       // apiService.releasePost(uri,headerMap,parms,file)
    }

    override fun getPresenter(url: String, headerMap: Map<String, Any>, clazz: Class<*>, parms: Map<String, Any>) {
        val apiService = RetrofitManager.INSTANCE.creat(ApiService::class.java)
        apiService.get(url, headerMap, parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val string = it.string()
                    var gson: Gson = Gson()
                    val it1 = gson.fromJson(string, clazz)
                    mView!!.View(it1)
                }

    }

    override fun postPresenter(url: String, headerMap: Map<String, Any>, clazz: Class<*>, parms: Map<String, Any>) {
        val apiService = RetrofitManager.INSTANCE.creat(ApiService::class.java)
        apiService.post(url, headerMap, parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val string = it.string()
                    var gson: Gson = Gson()
                    val it1 = gson.fromJson(string, clazz)
                    mView!!.View(it1)
                }
    }

    override fun putPresenter(url: String, headerMap: Map<String, Any>, clazz: Class<*>, parms: Map<String, Any>) {
        val apiService = RetrofitManager.INSTANCE.creat(ApiService::class.java)
        apiService.put(url, headerMap, parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val string = it.string()
                    var gson: Gson = Gson()
                    val it1 = gson.fromJson(string, clazz)
                    mView!!.View(it1)
                }
    }

    override fun deletePresenter(url: String, headerMap: Map<String, Any>, clazz: Class<*>, parms: Map<String, Any>) {
        val apiService = RetrofitManager.INSTANCE.creat(ApiService::class.java)
        apiService.delete(url, headerMap, parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val string = it.string()
                    var gson: Gson = Gson()
                    val it1 = gson.fromJson(string, clazz)
                    mView!!.View(it1)
                }
    }

}