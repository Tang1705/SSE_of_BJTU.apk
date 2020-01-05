package com.bjtu.wanciwang.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bjtu.wanciwang.R
import com.bjtu.wanciwang.databinding.ItemArticleBinding
import com.bjtu.wanciwang.entity.Article
import com.bjtu.wanciwang.view.article.ReadArticleActivity
import com.bjtu.wanciwang.view.main.ui.home.HomeViewModel

class RecycleViewAdapter(
    private var list: ArrayList<Article>,
    private val activity: Activity,
    val fragment: Fragment,
    private val type: Int
) :
    BaseBindingAdapter<ItemArticleBinding>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBoundViewHolder<ItemArticleBinding> {
        return DataBoundViewHolder(
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemArticleBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.article = list[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    init {
        this.setOnItemClickListener {
            if (type == 0) {
                val intent = Intent(activity, ReadArticleActivity::class.java)
                val position = it
                println(position)
                intent.putExtra("articleData", list[position])
                fragment.startActivity(intent)
                activity.overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha)
            } else {
                val intent = Intent(activity, ReadArticleActivity::class.java)
                val position = it
                intent.putExtra("articleData", list[position])
                activity.startActivity(intent)
                activity.overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha)
            }
        }
    }
}