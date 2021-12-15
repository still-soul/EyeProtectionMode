package com.ztk.eyeprotectionmode

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(){

    protected var createEyeCareViewHelper: CreateEyeCareViewHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLayout()
        if (canOpenEye()) {
            createEyeCareViewHelper = CreateEyeCareViewHelper(this, lifecycle)
        }
        initView(savedInstanceState)

    }

    private fun setLayout() {
        setContentView(getLayoutId())
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)


    /**
     * @return 是否需要开启护眼模式 默认开启
     */
    protected fun canOpenEye(): Boolean {
        return true
    }

}