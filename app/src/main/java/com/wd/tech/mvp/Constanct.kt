
package com.wd.tech.mvp

import com.wd.tech.base.BaseContract
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
    }
}