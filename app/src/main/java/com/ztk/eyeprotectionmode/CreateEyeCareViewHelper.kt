package com.ztk.eyeprotectionmode

import android.app.Activity
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Point
import android.text.format.Time
import android.view.Gravity
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * 护眼模式帮助类
 * @author xiaoman
 */
class CreateEyeCareViewHelper(private val activity: Activity, lifecycle: Lifecycle? = null) :
    LifecycleObserver {
    private var eyeCareView: FrameLayout? = null

    companion object {
        /**
         * 护眼模式关闭状态
         */
        const val eyeCloseType = 0

        /**
         * 护眼模式打开状态
         */
        const val eyeOpenType = 1

        /**
         * 护眼模式自动切换状态
         */
        const val eyeSwitchType = 2

        /**
         * 自动切换模式开始小时
         */
        const val beginHour = 20

        /**
         * 自动切换模式开始分钟
         */
        const val beginMin = 0

        /**
         * 自动切换模式结束小时
         */
        const val endHour = 6

        /**
         * 自动切换模式结束分钟
         */
        const val endMin = 0
    }

    init {
        lifecycle?.addObserver(this)
    }

    /**
     * 添加护眼模式浮层
     */
    private fun createEyeView() {
        if (eyeCareView != null) {
            return
        }
        eyeCareView = FrameLayout(activity)
        if (!isOpenEye()) {
            closeEye()
            return
        }
        val eyeCareViewParam = WindowManager.LayoutParams(
            WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                    or WindowManager.LayoutParams.FLAG_FULLSCREEN
                    or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSPARENT
        )

        eyeCareViewParam.gravity = Gravity.TOP or Gravity.START

        activity.windowManager.defaultDisplay.apply {
            val point = Point()
            this.getRealSize(point)
            eyeCareViewParam.width = point.x
            eyeCareViewParam.height = point.y
        }
        activity.windowManager.addView(eyeCareView, eyeCareViewParam)
        openEye()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(owner: LifecycleOwner?) {
        if (isOpenEye()) {
            //打开护眼模式
            openEye()
        } else {
            //关闭护眼模式
            closeEye()
        }
    }

    /**
     * 开启护眼模式
     */
    fun openEye() {
        if (eyeCareView != null) {
            if (isOpenEye()) {
                eyeCareView!!.setBackgroundColor(Color.parseColor("#4D000000"))
            } else {
                closeEye()
            }
        } else {
            createEyeView()
        }
    }


    /**
     * 关闭护眼模式
     */
    fun closeEye() {
        eyeCareView?.let {
            it.setBackgroundColor(Color.TRANSPARENT)
            if (it.isAttachedToWindow) {
                activity.windowManager.removeView(it)
            }
            eyeCareView = null
        }
    }

    /**
     * 判断当前系统时间是否在指定时间的范围内
     * [beginHour] 开始小时,例如22
     * [beginMin]  开始小时的分钟数,例如30
     * [endHour]   结束小时,例如 8
     * [endMin]    结束小时的分钟数,例如0
     * @return true表示在范围内, 否则false
     */
    private fun isCurrentInTimeScope(): Boolean {
        var result: Boolean
        val aDayInMillis = (1000 * 60 * 60 * 24).toLong()
        val currentTimeMillis = System.currentTimeMillis()
        val now = Time()
        now.set(currentTimeMillis)
        val startTime = Time()
        startTime.set(currentTimeMillis)
        startTime.hour = beginHour
        startTime.minute = beginMin
        val endTime = Time()
        endTime.set(currentTimeMillis)
        endTime.hour = endHour
        endTime.minute = endMin
        // 跨天的特殊情况(比如22:00-8:00)
        if (!startTime.before(endTime)) {
            startTime.set(startTime.toMillis(true) - aDayInMillis)
            result = !now.before(startTime) && !now.after(endTime)
            val startTimeInThisDay = Time()
            startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis)
            if (!now.before(startTimeInThisDay)) {
                result = true
            }
        } else {
            //普通情况(比如 8:00 - 14:00)
            result = !now.before(startTime) && !now.after(endTime)
        }
        return result
    }

    /**
     * 护眼模式是否打开
     */
    private fun isOpenEye(): Boolean {
        return SpUtil.getInstance(activity)
            .getIntValue(SpUtil.EYE_CARE_STYLE) == eyeOpenType || isSwitchEye()
    }

    /**
     * 护眼模式是否关闭
     */
    private fun isCloseEye(): Boolean {
        return SpUtil.getInstance(activity)
            .getIntValue(SpUtil.EYE_CARE_STYLE) == eyeCloseType || !isSwitchEye()
    }

    /**
     * 护眼模式自动切换状态是否在打开时间阶段
     */
    private fun isSwitchEye(): Boolean {
        return SpUtil.getInstance(activity)
            .getIntValue(SpUtil.EYE_CARE_STYLE) == eyeSwitchType && isCurrentInTimeScope()
    }



}