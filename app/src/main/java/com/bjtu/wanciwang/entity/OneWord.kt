package com.bjtu.wanciwang.entity

import android.media.SoundPool
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class OneWord(
    private var word_text: String,
    private var word_chinese: String,
    private var word_type: String,
    private var word_sym: String,
    private var word_help: String,
    private var word_exp: String,
    private var word_voice: SoundPool
) {


    fun getText(): String = word_text

    fun getChinese(): String = word_chinese

    fun getTYpe(): String = word_type

    fun getSym(): String = word_sym

    fun getHelpInfo(): String = word_help

    fun getUrl(): String = word_exp

    fun getName(): SoundPool = word_voice


}