package com.ztk.eyeprotectionmode

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast

class SettingActivity : BaseActivity() {
    private lateinit var ivOpenEye :ImageView
    private lateinit var ivCloseEye :ImageView
    private lateinit var ivSwitchEye :ImageView

    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initView(savedInstanceState: Bundle?) {
        ivOpenEye = findViewById(R.id.iv_open_eye)
        ivCloseEye = findViewById(R.id.iv_close_eye)
        ivSwitchEye = findViewById(R.id.iv_switch_eye)

        var type = SpUtil.getInstance(this).getIntValue(SpUtil.EYE_CARE_STYLE)
        when(type){
            //开启
            CreateEyeCareViewHelper.eyeOpenType ->{
                setOenStyle()
            }
            //关闭
            CreateEyeCareViewHelper.eyeCloseType ->{
                setCloseStyle()
            }
            //自动开启
            CreateEyeCareViewHelper.eyeSwitchType ->{
                setSwitchStyle()
            }

        }

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }



        findViewById<RelativeLayout>(R.id.rl_open_eye).setOnClickListener {
            if (type == CreateEyeCareViewHelper.eyeOpenType){
                return@setOnClickListener
            }
            setOenStyle()
            SpUtil.getInstance(this).setIntValue(SpUtil.EYE_CARE_STYLE, CreateEyeCareViewHelper.eyeOpenType)
            type = CreateEyeCareViewHelper.eyeOpenType
            createEyeCareViewHelper?.openEye()
            Toast.makeText(this,"已开启护眼模式",Toast.LENGTH_SHORT).show()
        }

        findViewById<RelativeLayout>(R.id.rl_close_eye).setOnClickListener {
            if (type == CreateEyeCareViewHelper.eyeCloseType){
                return@setOnClickListener
            }
            setCloseStyle()
            SpUtil.getInstance(this).setIntValue(SpUtil.EYE_CARE_STYLE, CreateEyeCareViewHelper.eyeCloseType)
            type = CreateEyeCareViewHelper.eyeCloseType
            createEyeCareViewHelper?.closeEye()
            Toast.makeText(this,"已关闭护眼模式",Toast.LENGTH_SHORT).show()
        }

        findViewById<RelativeLayout>(R.id.rl_switch_eye).setOnClickListener {
            if (type == CreateEyeCareViewHelper.eyeSwitchType){
                return@setOnClickListener
            }
            setSwitchStyle()
            SpUtil.getInstance(this).setIntValue(SpUtil.EYE_CARE_STYLE, CreateEyeCareViewHelper.eyeSwitchType)
            type = CreateEyeCareViewHelper.eyeSwitchType
            createEyeCareViewHelper?.openEye()
            Toast.makeText(this,"已开启自动切换模式",Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 开启样式
     */
    private fun setOenStyle() {
        ivOpenEye.visibility = View.VISIBLE
        ivCloseEye.visibility = View.GONE
        ivSwitchEye.visibility = View.GONE
    }

    /**
     * 关闭样式
     */
    private fun setCloseStyle() {
        ivOpenEye.visibility = View.GONE
        ivCloseEye.visibility = View.VISIBLE
        ivSwitchEye.visibility = View.GONE
    }

    /**
     * 自动开启样式
     */
    private fun setSwitchStyle() {
        ivOpenEye.visibility = View.GONE
        ivCloseEye.visibility = View.GONE
        ivSwitchEye.visibility = View.VISIBLE
    }
}