package com.bjtu.wanciwang.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("load_image")
fun loadImage(imageView: ImageView, url: String?) =
    Glide.with(imageView.context).load(url)
        .into(imageView)
