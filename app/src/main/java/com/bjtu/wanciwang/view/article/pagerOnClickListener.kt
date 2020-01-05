package com.bjtu.wanciwang.view.article

import android.content.Context
import android.view.View
import android.widget.Toast
import com.bjtu.wanciwang.R

class pagerOnClickListener(applicationContext: Context) : View.OnClickListener{
    internal lateinit var mContext: Context
    fun pagerOnClickListener(mContext: Context) {
        this.mContext = mContext
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.pager_img1 -> Toast.makeText(mContext, "图片1被点击", Toast.LENGTH_SHORT).show()
            R.id.pager_img2 -> Toast.makeText(mContext, "图片2被点击", Toast.LENGTH_SHORT).show()
            R.id.pager_img3 -> Toast.makeText(mContext, "图片3被点击", Toast.LENGTH_SHORT).show()
            R.id.pager_img4 -> Toast.makeText(mContext, "图片4被点击", Toast.LENGTH_SHORT).show()
            R.id.pager_img5 -> Toast.makeText(mContext, "图片5被点击", Toast.LENGTH_SHORT).show()
        }
    }
}