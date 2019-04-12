package com.wd.tech.base

open class BasePresenter<T : BaseContract.BaseView> : BaseContract.BasePresenter<T> {

    protected var mView: T? = null

    override fun attachView(view: T) {
        this.mView = view
    }

    override fun detachView() {
        this.mView = null
    }
}