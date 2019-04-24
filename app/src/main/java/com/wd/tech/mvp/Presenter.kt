package com.wd.tech.mvp

import android.util.Log
import com.google.gson.Gson
import com.luck.picture.lib.entity.LocalMedia
import com.wd.tech.api.ApiService
import com.wd.tech.base.BasePresenter
import com.wd.tech.bean.HeadBean
import com.wd.tech.utils.RetrofitManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File





class Presenter : BasePresenter<Constanct.View>(), Constanct.Presenter {
    override fun loadSend(uri: String,headMap: Map<String, Any>, content: String, selectList: MutableList<LocalMedia>) {
        var list : ArrayList<MultipartBody.Part> = ArrayList<MultipartBody.Part>()
        selectList.forEach {
            Log.e("123", it.path)
            val file = File(it.path)
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            list.add(body)
        }
        val apiService = RetrofitManager.INSTANCE.creat(ApiService::class.java)
        apiService.sendCommunity(uri,headMap,content,list)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mView!!.View(it)
                }
    }


    override fun imagePost(uri: String, headmap: Map<String, String>, image: MultipartBody.Part) {
        val apiService = RetrofitManager.INSTANCE.creat(ApiService::class.java)
        apiService.headicon(uri,headmap, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val string = it.string()
                    var gson: Gson = Gson()
                    val it1 = gson.fromJson(string, HeadBean::class.java)
                    mView!!.View(it1)
                }
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