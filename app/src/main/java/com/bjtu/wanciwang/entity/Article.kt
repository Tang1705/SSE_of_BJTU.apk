package com.bjtu.wanciwang.entity

import java.io.Serializable

class Article(
    private var name: String,
    private var date: String,
    private var content: String,
    private var url: String
) : Serializable {


    fun getName(): String = name

    fun getContent(): String = content

    fun getDate(): String = date


    fun getUrl(): String = url

}