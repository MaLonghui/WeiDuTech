package com.wd.tech.activity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.Toast
import com.facebook.common.util.UriUtil
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.AltruserBean
import com.wd.tech.bean.IndividualBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_individual_information.*
import kotlinx.android.synthetic.main.popup_camera.view.*
import java.io.File
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/*
*
*设置个人页面
*/
class IndividualInformationActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {

    var popupWindow: PopupWindow? = null
    //相机拍照的照片路径
    private val PHOTO_FLAG = 100
    private val CAIJIAN_FLAG = 200
    private val CAMERA_FLAG = 300
    private val path = Environment.getExternalStorageDirectory().toString() + "/image.png"
    override fun getLayoutId(): Int {
        return R.layout.activity_individual_information
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
//        返回
        back_setting.setOnClickListener {
            finish()
        }
//        退出登录
        back_login.setOnClickListener {
            val alert: AlertDialog.Builder =AlertDialog.Builder(this)
            alert.setIcon(R.drawable.icon)
            alert.setTitle("退出登录")
            alert.setMessage("是否进行退出登录")
            alert.setPositiveButton("是",DialogInterface.OnClickListener{dialogInterface, i ->
                val sp = this.getSharedPreferences("config", Context.MODE_PRIVATE)
                val edit = sp.edit()
                edit.putString("userId", "")
                edit.putString("session", "")
                edit.commit()
                finish()
            }).setNeutralButton("否",null)
                    .create()
                    .show()
        }
        //       修改密码
        res_pwd.setOnClickListener {
           startActivity(Intent(this@IndividualInformationActivity,ChangePasswordActivity::class.java))
        }
        val sp = this.getSharedPreferences("config", Context.MODE_PRIVATE)
        val userid = sp.getString("userId", "")
        val session = sp.getString("sessionId", "")
//        请求头
        val map: Map<String, Any> = mapOf(Pair("userId", userid), Pair("sessionId", session))
//        参数
        val mapcan: Map<String, Any> = mapOf()

        mPresenter!!.getPresenter(Api.INDIVIDUALINFORMATION, map, IndividualBean::class.java, mapcan)
        setting_name.setOnClickListener {
            val name = setting_name.text.toString().trim()
            //请求头
            val map: Map<String, Any> = mapOf(Pair("userId", userid), Pair("sessionId", session))
//        参数
            val mapcan: Map<String, Any> = mapOf(Pair("nickName", name))
            mPresenter!!.putPresenter(Api.ALTERUSER, map, AltruserBean::class.java, mapcan)
        }

//        修改签名
        next_to.setOnClickListener {

            startActivity(Intent(this@IndividualInformationActivity, NextActivity::class.java))
        }
//        图片
        val v = View.inflate(this@IndividualInformationActivity, R.layout.popup_camera, null)
        popupWindow = PopupWindow(
                v, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true
        )
        popupWindow!!.setFocusable(true)
        popupWindow!!.setTouchable(true)
        popupWindow!!.setBackgroundDrawable(BitmapDrawable())
        v.camera.setOnClickListener {
            //MediaStore.ACTION_IMAGE_CAPTURE 打开相机的Action
            val intent_takePhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //存放到内存中
            intent_takePhoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(File(path)))
            startActivityForResult(intent_takePhoto, PHOTO_FLAG)
            popupWindow!!.dismiss()
        }
        v.picture.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            //设置图片的格式
            intent.type = "image/*"
            startActivityForResult(intent, CAMERA_FLAG)
            popupWindow!!.dismiss()
        }
        v.popup_cancle.setOnClickListener {
            popupWindow!!.dismiss()
        }
        setting_image.setOnClickListener {
            popupWindow!!.showAtLocation(it,Gravity.BOTTOM, 0, 0)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PHOTO_FLAG && resultCode == RESULT_OK) {//相机返回的数据
            crop(Uri.fromFile(File(path)))
        } else if (requestCode == CAMERA_FLAG && resultCode == RESULT_OK) {//相册返回的数据
            //得到图片的全路径
            if (data != null) {
                val uri = data.data
                crop(uri)
            }
        } else if (requestCode == CAIJIAN_FLAG && resultCode == RESULT_OK) {//剪切回来的照片数据
            if (data != null) {
                val bitmap = data.getParcelableExtra<Bitmap>("data")
                val list = ArrayList<Any>()
                list.add(bitmap)
                val file:File=File("data")
                val img_path = bitmapToString(bitmap)
                setting_image.setImageURI(UriUtil.parseUriOrNull("file://$img_path"))
                val sp = this.getSharedPreferences("config", Context.MODE_PRIVATE)
                val userId = sp.getString("userId", "")
                val session = sp.getString("sessionId", "")
                val map: HashMap<String, String> = hashMapOf(Pair("userId", userId), Pair("sessionId", session))
                val mapHead = HashMap<String, String>()
                mapHead.put("image",img_path)
//                mPresenter!!.imagePost(Api.HEAD,map,mapHead)
            }
        }
    }
    //bitMap转换为file(转file不一定对)
    fun bitmapToString(bitmap: Bitmap): String {
        //将bitmap转换为uri
        val uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null))

        val proj = arrayOf(MediaStore.Images.Media.DATA)

        val actualimagecursor = managedQuery(uri, proj, null, null, null)

        val actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

        actualimagecursor.moveToFirst()

        return actualimagecursor.getString(actual_image_column_index)
    }


    //裁剪图片
    private fun crop(uri: Uri) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        //支持裁剪
        intent.putExtra("CROP", true)
        //裁剪的比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        //裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250)
        intent.putExtra("outputY", 250)
        //将图片返回给data
        intent.putExtra("return-data", true)
        startActivityForResult(intent, CAIJIAN_FLAG)
    }
    //    隐藏键盘
    fun hideSoftInputMethod(ed: EditText) {
        val currentVersion: Int = android.os.Build.VERSION.SDK_INT
        var methodName: String? = null
        if (currentVersion >= 16) {
//                4.2
            methodName = "setShowSoftInputOnFocus"
        } else if (currentVersion >= 14) {
//                4.0
            methodName = "setSoftInputShownOnFocus"
        }
        if (methodName == null) {
            ed.setInputExtras(InputType.TYPE_NULL)
        } else {
            val cls: Class<EditText> = EditText::class.java
            var setShowSoftInputOnFocus: Method? = null
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, Boolean::class.java)
                setShowSoftInputOnFocus.isAccessible = true
                setShowSoftInputOnFocus.invoke(ed, false)
            } catch (e: NoSuchMethodException) {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    override fun View(any: Any) {
        if (any is IndividualBean) {
            val bean: IndividualBean = any
            if (bean.status.equals("0000")) {
                setting_image.setImageURI(bean.result.headPic)
                setting_name.setText(bean.result.nickName)
                setting_phone.setText(bean.result.phone)
                val sex = bean.result.sex
                if (sex == 1) {
                    user_sex.setText("男")
                } else {
                    user_sex.setText("女")
                }
                email.setText("")
                time.setText("")
                JiFen.setText("${bean.result.integral}")
                if (bean.result.whetherVip == 2) {
                    vip.setText("普通用户")
                } else {
                    vip.setText("vip用户")
                }
            }
        }
        if (any is AltruserBean) {
            val alterBean: AltruserBean = any
            if (alterBean.status.equals("0000")) {
                Toast.makeText(this@IndividualInformationActivity, alterBean.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}

