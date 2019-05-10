package com.nanda.androidtheme.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nanda.androidtheme.R
import com.nanda.androidtheme.data.model.Theme
import com.nanda.androidtheme.helpers.ThemeView
import com.nanda.androidtheme.ui.activity.HomeActivity
import com.nanda.androidtheme.ui.base.BaseActivity

class ThemeAdapter(
    private val themeList: List<Theme>,
    private val mRecyclerViewClickListener: RecyclerViewClickListener
) : RecyclerView.Adapter<ThemeAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_row_theme, parent, false)

        return MyViewHolder(itemView, mRecyclerViewClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val theme = themeList[position]
        holder.themeView.setTheme(theme)

        if (HomeActivity.selectedTheme == position) {
            holder.themeView.isActivated = true
        } else {
            holder.themeView.isActivated = false
        }
    }

    override fun getItemCount(): Int {
        return themeList.size
    }

    inner class MyViewHolder(view: View, private val mListener: RecyclerViewClickListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        var themeView: ThemeView

        init {
            themeView = view.findViewById<View>(R.id.themeView) as ThemeView
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            mListener.onClick(view, adapterPosition)
            HomeActivity.selectedTheme = adapterPosition
            BaseActivity.mTheme = HomeActivity.mThemeList[adapterPosition].id
            themeView.isActivated = true
            this@ThemeAdapter.notifyDataSetChanged()
        }
    }
}