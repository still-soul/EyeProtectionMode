package com.ztk.eyeprotectionmode;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpUtil {

    /**
     * 护眼模式状态
     */
    public static final String EYE_CARE_STYLE = "eye_care_style";


    protected SharedPreferences sp;
    private static SpUtil mInstance;

    public static SpUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SpUtil(context);
        }
        return mInstance;
    }

    private SpUtil(Context context) {
        sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
    }
    /**
     * 写入数据
     *
     * @param key
     * @param value
     */
    public void setValue(String key, String value) {
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setValueWithApply(String key, String value) {
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 根据key读取数据
     *
     * @param key
     * @return
     */
    public String getValue(String key) {

        return sp.getString(key, "");
    }


    public String getValue(String key, String defaultKey) {
        return sp.getString(key, defaultKey);
    }

    /**
     * 根据key读取数据
     *
     * @param key
     * @return
     */
    public int getIntValue(String key) {
        return sp.getInt(key, 0);
    }

    /**
     * 根据key读取数据
     *
     * @param key
     * @return
     */
    public int getIntValue(String key, int defValue) {
        return sp.getInt(key, defValue);
    }
    /**
     * 根据key读取long数据
     *
     * @param key
     * @return
     */
    public long getLongValue(String key,long defValue) {
        return sp.getLong(key, defValue);
    }

    public Boolean getBooleanValue(String key) {
        return sp.getBoolean(key, false);
    }

    public Boolean getBooleanValue(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    /**
     * 写入数据
     *
     * @param key
     * @param value
     */
    public void setBooleanValue(String key, boolean value) {
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setBooleanValueWithApply(String key, boolean value) {
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 写入数据
     *
     * @param key
     * @param value
     */
    public void setIntValue(String key, int value) {
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void setIntValueWithApply(String key, int value) {
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 写入long数据
     *
     * @param key
     * @param value
     */
    public void setLongValue(String key, long value) {
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    public void remove(String key) {
        sp.edit().remove(key).commit();
    }



}
