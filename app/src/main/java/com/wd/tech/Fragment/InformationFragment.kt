package com.wd.tech.Fragment
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseFragment
import com.wd.tech.bean.BannerBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter


/**
 * 资讯页面
 */
class InformationFragment : BaseFragment<Constanct.View, Presenter>(), Constanct.View {
    override fun getLayoutId(): Int {
        return R.layout.fragment_information
    }

    override fun initPresenter(): Presenter {
        return Presenter()
    }


    override fun initData() {
        val headMap: Map<String, Any> = mapOf()
        val bannerPrams: Map<String, Any> = mapOf()
        mPresenter!!.getPresenter(Api.TECH_BANNER, headMap, BannerBean::class.java, bannerPrams)
    }

    override fun View(any: Any) {
        var bannerBean = any as BannerBean
        Toast.makeText(activity, bannerBean.message, Toast.LENGTH_LONG).show()
    }


}
