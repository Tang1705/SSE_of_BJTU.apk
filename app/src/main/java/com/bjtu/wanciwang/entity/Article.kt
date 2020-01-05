package com.bjtu.wanciwang.entity

import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class Article(
    private var name: String,
    private var date: String,
    private var content: String,
    private var url: String
) {


    fun getName(): String = name

    fun getContent(): String = content

    fun getDate(): String = date


    fun getUrl(): String = url

}