package com.bjtu.wanciwang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bjtu.wanciwang.databinding.ItemArticleBinding
import com.bjtu.wanciwang.entity.Article

class RecycleViewAdapter(private var list: ArrayList<Article>) :
    BaseBindingAdapter<ItemArticleBinding>() {

    inner class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {


        override fun onClick(v: View?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

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

}