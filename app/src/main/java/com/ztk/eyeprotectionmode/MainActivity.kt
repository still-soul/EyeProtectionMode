package com.ztk.eyeprotectionmode

import android.content.Intent
import android.os.Bundle
import android.widget.TextView

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        findViewById<TextView>(R.id.tvSetting).setOnClickListener {
            startActivity(Intent(this,SettingActivity::class.java))
        }
    }


}