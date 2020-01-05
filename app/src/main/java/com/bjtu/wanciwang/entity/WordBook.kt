package com.bjtu.wanciwang.entity

import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class WordBook(
    private var bookid: String,
    private var book_name: String,
    private var book_visit: Boolean,
    private var book_size: Int
) {


    fun getId(): String = bookid


    fun getName(): String = book_name


    fun getVisit(): Boolean = book_visit


    fun getSize(): Int = book_size

}