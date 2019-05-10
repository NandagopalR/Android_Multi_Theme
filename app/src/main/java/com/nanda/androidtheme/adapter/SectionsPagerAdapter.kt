package com.nanda.androidtheme.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.nanda.androidtheme.ui.fragment.CardFragment
import com.nanda.androidtheme.ui.fragment.ChatFragment
import com.nanda.androidtheme.ui.fragment.TextFragment
import com.nanda.androidtheme.ui.fragment.UIFragment

class SectionsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var mWordUpdated: String? = null

    //call this method to update fragments in ViewPager dynamically
    fun update(word: String) {
        this.mWordUpdated = word
        notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        //don't return POSITION_NONE, avoid fragment recreation.
        return super.getItemPosition(`object`)
    }

    override fun getItem(position: Int): Fragment? {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = TextFragment.newInstance("", "")
            1 -> fragment = CardFragment.newInstance("", "")
            2 -> fragment = UIFragment.newInstance("", "")
            3 -> fragment = ChatFragment.newInstance("", "")
            else -> {
            }
        }
        return fragment
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Text"
            1 -> return "Card"
            2 -> return "UI"
            3 -> return "Chat"
        }
        return null
    }
}