package com.wd.tech.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import com.wd.tech.R
import com.wd.tech.base.BaseActivity
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_community_release.*
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import com.huburt.library.ImagePicker
import com.huburt.library.bean.ImageItem
import com.huburt.library.util.Utils
import com.huburt.library.view.GridSpacingItemDecoration
import com.wd.tech.adapter.ImageAdapter


class CommunityReleaseActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View, View.OnClickListener, ImagePicker.OnPickImageResultListener {


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_camera -> {
                //直接打开相机
                ImagePicker.camera(this@CommunityReleaseActivity, this@CommunityReleaseActivity)
                closePopupWindow()
            }
            R.id.tv_album -> {
                //选择图片，第二次进入会自动带入之前选择的图片（未重置图片参数）
                ImagePicker.pick(this@CommunityReleaseActivity, this@CommunityReleaseActivity)
                closePopupWindow()
            }
            R.id.tv_cancel -> {
                closePopupWindow()
            }
            else ->{
                closePopupWindow()
            }
        }
    }


    var pop: PopupWindow? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_community_release
    }


    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }
    override fun onImageResult(imageItems: ArrayList<ImageItem>) {
        (recycer_release.adapter as ImageAdapter).updateData(imageItems)
    }
    override fun initData() {
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var content = ""
        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        val string = release_edit.text.toString()
        content = string
        //使用自定义默认参数或者默认参数,并清除Application启动之后选择图片缓存
        ImagePicker.defaultConfig()
        ImagePicker.isCrop(false)
        ImagePicker.multiMode(true)
        //监听输入框的字符数
        release_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                edit_count.text = s!!.length.toString() + "/200"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        release_back.setOnClickListener {
            onBackPressed()
        }
        recycer_release.layoutManager = GridLayoutManager(this, 3)
        val imageAdapter = ImageAdapter(ArrayList())
        imageAdapter.listener = object : ImageAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //回顾已选择图片，可以删除
                ImagePicker.review(this@CommunityReleaseActivity, position, this@CommunityReleaseActivity)
            }
        }
        recycer_release.addItemDecoration(GridSpacingItemDecoration(3, Utils.dp2px(this, 2f), false))
        recycer_release.adapter = imageAdapter
        imageAdapter.ImgList {
            //Toast.makeText(this,it[0].path,Toast.LENGTH_LONG).show()
        }



        //发表
        release_fabiao.setOnClickListener {


        }






        initWidget()
    }

    override fun onDestroy() {
        super.onDestroy()
        ImagePicker.clear()//清除缓存已选择的图片
    }
    private fun initWidget() {
        val layoutManager = GridLayoutManager(this, 3)
        recycer_release.layoutManager = layoutManager
        img_add.setOnClickListener {
            showPop()
        }

    }

    private fun showPop() {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_dialog, null)
        val tv_camera = view.findViewById<LinearLayout>(R.id.tv_camera)
        val tv_album = view.findViewById<LinearLayout>(R.id.tv_album)
        val tv_cancel = view.findViewById<TextView>(R.id.tv_cancel)
        pop = PopupWindow(view, -1, -2);
        pop!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        pop!!.isOutsideTouchable = true
        pop!!.isFocusable = true
        val lp = window.attributes
        lp.alpha = 0.5f
        window.attributes = lp
        pop!!.setOnDismissListener {
            val lp = window.attributes
            lp.alpha = 1f
            window.attributes = lp
        }
        pop!!.animationStyle = R.style.main_menu_photo_anim
        pop!!.showAtLocation(window.decorView, Gravity.BOTTOM, 0, 0)
        tv_camera.setOnClickListener(this)
        tv_album.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)

    }

    private fun closePopupWindow() {
        if (pop != null && pop!!.isShowing()) {
            pop!!.dismiss()
            pop = null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun View(any: Any) {

    }


}
