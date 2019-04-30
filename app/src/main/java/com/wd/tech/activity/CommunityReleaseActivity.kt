package com.wd.tech.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Looper
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
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
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.wd.tech.adapter.GridImageAdapter
import com.wd.tech.api.Api
import com.wd.tech.bean.TaskBean
import com.wd.tech.bean.UserPublicBean
import com.wd.tech.utils.FullyGridLayoutManager
import java.util.ArrayList


class CommunityReleaseActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View, View.OnClickListener {
    private val maxSelectNum = 9
    private val selectList = ArrayList<LocalMedia>()
    private var adapter: GridImageAdapter? = null
    private var pop: PopupWindow? = null


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_camera -> {
                //直接打开相机
                //拍照
                PictureSelector.create(this@CommunityReleaseActivity)
                        .openCamera(PictureMimeType.ofImage())
                        .forResult(PictureConfig.CHOOSE_REQUEST)
                closePopupWindow()
            }
            R.id.tv_album -> {
                //选择图片，第二次进入会自动带入之前选择的图片（未重置图片参数）
                //相册
                PictureSelector.create(this@CommunityReleaseActivity)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(maxSelectNum)
                        .minSelectNum(1)
                        .imageSpanCount(4)
                        .compress(true)
                        .selectionMode(PictureConfig.MULTIPLE)
                        .forResult(PictureConfig.CHOOSE_REQUEST)
                closePopupWindow()
            }
            R.id.tv_cancel -> {
                closePopupWindow()
            }
            else -> {
                closePopupWindow()
            }
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_community_release
    }


    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        var content = ""
        initWidget()
        //监听输入框的字符数
        release_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                edit_count.text = s!!.length.toString() + "/200"
                content = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        release_back.setOnClickListener {
            onBackPressed()
        }

        //发表
        release_fabiao.setOnClickListener {
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(this, "请输入要发表的内容", Toast.LENGTH_LONG).show()
            } else {
                if (selectList!!.size > 0) {
                    mPresenter!!.loadSend(Api.COMMUNITY_RELEASE, sHeadMap, content, selectList)
                } else {
                    var list: MutableList<LocalMedia> = listOf<LocalMedia>().toMutableList()
                    mPresenter!!.loadSend(Api.COMMUNITY_RELEASE, sHeadMap, content, list)
                }
            }

            mPresenter!!.loadSend(Api.COMMUNITY_RELEASE,sHeadMap,content,selectList)
            val mapcanthress: Map<String, Int> = mapOf(Pair("taskId",1003))
            mPresenter!!.postPresenter(Api.ZUOTASK, sHeadMap, TaskBean::class.java, mapcanthress)
        }


    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun initWidget() {

        val manager = FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)

        recycer_release.layoutManager = manager
        adapter = GridImageAdapter(this)
        adapter!!.onAddPicClickListener {
            showPop()
        }
        adapter!!.setList(selectList)
        adapter!!.setSelectMax(maxSelectNum)
        recycer_release.adapter = adapter
        adapter!!.setOnItemClickListener(object : GridImageAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, v: View) {
                if (selectList.size > 0) {
                    val media = selectList[position]
                    val pictureType = media.pictureType
                    val mediaType = PictureMimeType.pictureToVideo(pictureType)
                    when (mediaType) {
                        1 -> {
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(this@CommunityReleaseActivity).externalPicturePreview(position, selectList)
                        }
                        2 -> {
                            // 预览视频
                            PictureSelector.create(this@CommunityReleaseActivity).externalPictureVideo(media.path)
                        }
                        3 -> {
                            // 预览音频
                            PictureSelector.create(this@CommunityReleaseActivity).externalPictureAudio(media.path)
                        }
                    }
                }
            }

        })

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
        val images: List<LocalMedia>
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    images = PictureSelector.obtainMultipleResult(data)
                    selectList.addAll(images)
                    adapter!!.setList(selectList)
                    adapter!!.notifyDataSetChanged()
                }
            }
        }

    }

    override fun View(any: Any) {
        if (any is UserPublicBean) {
            var userPublicBean: UserPublicBean = any
           // Toast.makeText(this@CommunityReleaseActivity, userPublicBean.message, Toast.LENGTH_LONG).show()
            if (userPublicBean.status.equals("0000")) {
                finish()
            }
        }
    }


}
