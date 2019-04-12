package com.wd.tech.mvp
import com.google.gson.Gson
import com.wd.tech.api.ApiService
import com.wd.tech.base.BasePresenter
import com.wd.tech.utils.RetrofitManager
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers


class Presenter() : BasePresenter<Constanct.View>(), Constanct.Presenter {
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

    override fun deletePresenter(url: String, headerMap: Map<String, Any>, clazz: Class<*>, parms: Map<String, Any>) {
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

}