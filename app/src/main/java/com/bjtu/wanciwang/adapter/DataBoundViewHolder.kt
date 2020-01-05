package com.bjtu.wanciwang.adapter

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bjtu.wanciwang.R
import com.bjtu.wanciwang.databinding.ItemArticleBinding
import com.bjtu.wanciwang.entity.Article
import com.bjtu.wanciwang.view.main.ui.notifications.item_view
import java.util.ArrayList

class DataBoundViewHolder<T : ViewDataBinding>(
    val binding: T

) : RecyclerView.ViewHolder(binding.root) {

}