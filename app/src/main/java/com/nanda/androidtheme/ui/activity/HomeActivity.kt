package com.nanda.androidtheme.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.*
import android.view.View
import android.widget.CompoundButton
import android.widget.LinearLayout
import com.nanda.androidtheme.R
import com.nanda.androidtheme.adapter.RecyclerViewClickListener
import com.nanda.androidtheme.adapter.SectionsPagerAdapter
import com.nanda.androidtheme.adapter.ThemeAdapter
import com.nanda.androidtheme.data.model.Theme
import com.nanda.androidtheme.helpers.ThemeView
import com.nanda.androidtheme.ui.base.BaseActivity
import com.nanda.androidtheme.utils.ThemeUtil
import java.util.*

class HomeActivity : BaseActivity(), View.OnClickListener {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var mViewPager: ViewPager? = null

    companion object {
        var mThemeList: MutableList<Theme> = ArrayList()
        var selectedTheme = 0
    }

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: ThemeAdapter? = null
    private var mBottomSheetBehavior: BottomSheetBehavior<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        initBottomSheet()

        prepareThemeData()

        val themeView = findViewById<ThemeView>(R.id.theme_selected)
        themeView.setTheme(mThemeList[selectedTheme])

        //------------ view pager and tabs
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container) as ViewPager
        mViewPager!!.setAdapter(mSectionsPagerAdapter)

        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)
    }

    override fun onClick(view: View?) {
        when (view!!.getId()) {
            R.id.theme_selected, R.id.fab ->
                // change the state of the bottom sheet
                when (mBottomSheetBehavior!!.getState()) {
                    BottomSheetBehavior.STATE_HIDDEN -> mBottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)

                    BottomSheetBehavior.STATE_COLLAPSED -> mBottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED)

                    BottomSheetBehavior.STATE_EXPANDED -> mBottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
                }
        }
    }

    private fun initBottomSheet() {
        // get the bottom sheet view
        val llBottomSheet = findViewById(R.id.bottom_sheet) as LinearLayout

        // init the bottom sheet behavior
        mBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet)

        val switchCompat = findViewById<SwitchCompat>(R.id.switch_dark_mode)
        switchCompat.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            mIsNightMode = b
            var delayTime = 200
            if (mBottomSheetBehavior!!.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                delayTime = 400
                mBottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
            compoundButton.postDelayed({
                if (mIsNightMode) {
                    delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }, delayTime.toLong())
        })

        mRecyclerView = findViewById(R.id.recyclerView)

        mAdapter = ThemeAdapter(mThemeList, object : RecyclerViewClickListener {
            override fun onClick(view: View, position: Int) {
                mBottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
                view.postDelayed({ this@HomeActivity.recreate() }, 400)
            }
        })

        val mLayoutManager = GridLayoutManager(applicationContext, 4)
        mRecyclerView!!.setLayoutManager(mLayoutManager)
        mRecyclerView!!.setItemAnimator(DefaultItemAnimator())
        mRecyclerView!!.setAdapter(mAdapter)
    }

    private fun prepareThemeData() {
        mThemeList.clear()
        mThemeList.addAll(ThemeUtil.themeList)
        mAdapter!!.notifyDataSetChanged()
    }

}
