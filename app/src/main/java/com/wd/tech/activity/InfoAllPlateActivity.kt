package com.wd.tech.activity

import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.adapter.InfoPlateAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.InfoPlateBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.GridSpacingItemDecoration
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.activity_info_all_plate.*
import kotlinx.android.synthetic.main.fragment_information.*

class InfoAllPlateActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var infoPlateAdapter: InfoPlateAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_info_all_plate
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        var headMap: Map<String, Any> = mapOf()
        var prams: Map<String, Any> = mapOf()
        mPresenter!!.getPresenter(Api.INFO_PLATE, headMap, InfoPlateBean::class.java, prams)
        var gridLayoutManager = GridLayoutManager(this, 2)
        info_plate_recycler.layoutManager = gridLayoutManager
        infoPlateAdapter = InfoPlateAdapter(this)
        info_plate_recycler.adapter = infoPlateAdapter
        infoPlateAdapter!!.setItemListener { i, s ->
            var intPrams:Map<String,Any> = mapOf(Pair("id",i), Pair("title",s))
            JumpActivityUtils.skipValueActivity(this,PlateDetailsActivity::class.java, intPrams as HashMap<String, out Any>)
        }

    }

    override fun View(any: Any) {
        if (any != null) {
            var infoPlateBean: InfoPlateBean = any as InfoPlateBean
            val plateList = infoPlateBean.result
            infoPlateAdapter!!.setPlaList(plateList)
        }
    }
}
