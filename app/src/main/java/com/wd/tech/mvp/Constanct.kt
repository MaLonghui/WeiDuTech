
package com.wd.tech.mvp

import com.luck.picture.lib.entity.LocalMedia
import com.wd.tech.base.BaseContract
import java.io.File
import okhttp3.MultipartBody

class Constanct {
    interface View : BaseContract.BaseView{
        fun View(any: Any)
    }

    interface Presenter : BaseContract.BasePresenter<View>{
        fun getPresenter(uri: String,headerMap: Map<String,Any>,clazz: Class<*>,parms:Map<String,Any>)
        fun postPresenter(uri: String, headerMap: Map<String,Any>, clazz: Class<*>, parms:Map<String,Any>)
        fun putPresenter(uri: String, headerMap: Map<String,Any>, clazz: Class<*>, parms:Map<String,Any>)
        fun deletePresenter(uri: String, headerMap: Map<String,Any>, clazz: Class<*>, parms:Map<String,Any>)
        fun imagePost(uri:String, headmap: Map<String, String >, image: MultipartBody.Part)
        fun loadSend(uri: String,headMap: Map<String, Any>,content : String,selectList : MutableList<LocalMedia>)
    }
}