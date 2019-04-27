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
import okhttp3.MultipartBody
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File





class Presenter : BasePresenter<Constanct.View>(), Constanct.Presenter {
    override fun headIconPresenter(url: String,headMap: Map<String, Any>, file: File) {
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val apiService = RetrofitManager.INSTANCE.creat(ApiService::class.java)
        apiService.headicon(url,headMap,body)
                .subscribeOn(Schedulers.io

                ())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mView!!.View(it)
                }
    }

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