
package com.wd.tech.mvp

import com.luck.picture.lib.entity.LocalMedia
import com.wd.tech.base.BaseContract
import okhttp3.HttpUrl
import okhttp3.MultipartBody
import java.io.File
class Constanct {
    interface View : BaseContract.BaseView{
        fun View(any: Any)
    }

    interface Presenter : BaseContract.BasePresenter<View>{
        fun getPresenter(uri: String,headerMap: Map<String,Any>,clazz: Class<*>,parms:Map<String,Any>)
        fun postPresenter(uri: String, headerMap: Map<String,Any>, clazz: Class<*>, parms:Map<String,Any>)
        fun putPresenter(uri: String, headerMap: Map<String,Any>, clazz: Class<*>, parms:Map<String,Any>)
        fun deletePresenter(uri: String, headerMap: Map<String,Any>, clazz: Class<*>, parms:Map<String,Any>)
        fun imgsPostPresenter(uri: String, headerMap: Map<String,Any>, parms:Map<String,Any>, file: File, clazz: Class<*>)
        fun loadSend(uri: String,headMap: Map<String, Any>,content : String,selectList : MutableList<LocalMedia>)
        fun headIconPresenter(url: String,headMap: Map<String, Any>, file: File)
    }
}