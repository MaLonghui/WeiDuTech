package com.wd.tech.activity

import android.content.Context
import android.content.Intent
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.TaskBean
import com.wd.tech.bean.UserPublicBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.PhoneFormatCheckUtils
import kotlinx.android.synthetic.main.activity_perfect_avtivity.*
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.util.*

class PerfectAvtivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var sumsex: Int = 1

    override fun getLayoutId(): Int {
        return R.layout.activity_perfect_avtivity
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {

        val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        text_save.setOnClickListener {
            val name = text_name.text.toString().trim()
            val signature = text_signature.text.toString().trim()
            val trim = text_sex.text.toString().trim()
            val birth = text_birth.text.toString().trim()
            if (trim.equals("男")) {
                sumsex = 1
            } else if (trim.equals("女")) {
                sumsex = 2
            }
            val s = text_email.text.toString().trim()
            val email = PhoneFormatCheckUtils.isEmail(s)
            val mapcan: Map<String, String> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
            val mapcanperfect: Map<String, Any> = mapOf(Pair("nickName", name), Pair("sex", sumsex), Pair("signature", signature), Pair("birthday", birth), Pair("email", s))
            mPresenter!!.postPresenter(Api.PERFECT, mapcan, UserPublicBean::class.java, mapcanperfect)
           // Toast.makeText(this, "${mapcanperfect}", Toast.LENGTH_LONG).show()

        }

        text_sex.setOnClickListener {
            selectSex()
        }
        hideSoftInputMethod(text_birth)
        text_birth.setOnClickListener {
            timeSelect()
        }
        attention_back.setOnClickListener {
            onBackPressed()
        }
    }

    fun timeSelect() {
        /*val mTimePickerView = TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY)
        // 设置是否循环
        mTimePickerView.setCyclic(true)
        // 设置滚轮文字大小
        mTimePickerView.setTextSize(TimePickerView.TextSize.SMALL)
        // 设置时间可选范围(结合 setTime 方法使用,必须在)
        //Calendar calendar = Calendar.getInstance();
        // mTimePickerView.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR));
        // 设置选中时间
        // mTimePickerView.setTime(new Date());
        mTimePickerView.setOnTimeSelectListener(object : TimePickerView.OnTimeSelectListener {
            override fun onTimeSelect(date: Date) {
                //时期格式
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                text_birth.setText(format.format(date))
            }
        })*/
        //显示
        /*mTimePickerView.show()*/
    }

    //    隐藏键盘
    fun hideSoftInputMethod(ed: EditText) {

        val currentVersion = android.os.Build.VERSION.SDK_INT
        var methodName: String? = null
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus"
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus"
        }

        if (methodName == null) {
            ed.setInputType(InputType.TYPE_NULL)
        } else {
            val cls = EditText::class.java
            val setShowSoftInputOnFocus: Method
            try {
                setShowSoftInputOnFocus = cls!!.getMethod(methodName, Boolean::class.javaPrimitiveType!!)
                setShowSoftInputOnFocus.setAccessible(true)
                setShowSoftInputOnFocus.invoke(ed, false)
            } catch (e: NoSuchMethodException) {
                ed.setInputType(InputType.TYPE_NULL)
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 选择性别
     */
    private fun selectSex() {
        //val mOptionsPickerView: OptionsPickerView<String> = OptionsPickerView(this)
        val list = ArrayList<String>()
        list.add("男")
        list.add("女")
      /*  // 设置数据
        mOptionsPickerView.setPicker(list)
        // 设置选项单位
        mOptionsPickerView.setOnOptionsSelectListener { option1, option2, option3 ->
            val sex = list[option1]
            text_sex.setText(sex)
        }
        mOptionsPickerView.show()*/
    }

    override fun View(any: Any) {
        if (any is UserPublicBean) {
            val bean: UserPublicBean = any
            if (bean.status.equals("0000")) {
                val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
                val userId = sp.getString("userId", "")
                val sessionId = sp.getString("sessionId", "")
                val mapcan: Map<String, String> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
                val mapcansix: Map<String, Int> = mapOf(Pair("taskId",1006))
                mPresenter!!.postPresenter(Api.ZUOTASK, mapcan, TaskBean::class.java, mapcansix)
                Toast.makeText(this, bean.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
