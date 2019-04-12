package com.wd.tech.base

interface BaseContract {

    interface BaseView {

    }

    interface BasePresenter<in T> {
        fun attachView(view: T)
        fun detachView()
    }
}
