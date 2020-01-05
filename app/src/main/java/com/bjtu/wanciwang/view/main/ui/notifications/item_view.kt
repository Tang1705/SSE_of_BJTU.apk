package com.bjtu.wanciwang.view.main.ui.notifications

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bjtu.wanciwang.R

/**
 * 创建人: Fang.Mr
 * 创建时间:2018-01-23
 * 功能描述:
 */
class item_view @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : LinearLayout(context, attrs, defStyleAttr) {
    private val imageView //item的图标
            : ImageView
    private val textView //item的文字
            : TextView
    private val bottomview: ImageView
    private var isbootom = true //是否显示底部的下划线
    private fun initview() {
        if (isbootom) {
            bottomview.visibility = View.VISIBLE
        } else {
            bottomview.visibility = View.GONE
        }
    }

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.activity_item_view, this)
        /* LayoutInflater mInflater = LayoutInflater.from(context);
        View myView = mInflater.inflate(R.layout.item_view, null);
        addView(myView);*/
        val ta = context.obtainStyledAttributes(attrs, R.styleable.item_view)
        isbootom = ta.getBoolean(R.styleable.item_view_show_bottomline, true)
        bottomview = findViewById(R.id.item_bottom)
        imageView = findViewById(R.id.item_img)
        textView = findViewById(R.id.item_text)
        textView.text = ta.getString(R.styleable.item_view_show_text)
        imageView.setBackgroundResource(
            ta.getResourceId(
                R.styleable.item_view_show_leftimg,
                R.drawable.setting
            )
        )
        ta.recycle()
        initview()
    }
}