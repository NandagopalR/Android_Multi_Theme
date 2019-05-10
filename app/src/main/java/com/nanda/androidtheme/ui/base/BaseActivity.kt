package com.nanda.androidtheme.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nanda.androidtheme.utils.ThemeUtil

import com.nanda.androidtheme.utils.ThemeUtil.THEME_RED

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(ThemeUtil.getThemeId(mTheme))
    }

    companion object {
        var mTheme = THEME_RED
        var mIsNightMode = false
    }


}
