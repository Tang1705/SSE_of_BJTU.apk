package com.bjtu.wanciwang.view.article

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bjtu.wanciwang.R
import com.bjtu.wanciwang.entity.Article
import com.bjtu.wanciwang.view.article.Fragment.ListBottomSheetDialogFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_read_article.*


class ReadArticleActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.bjtu.wanciwang.R.layout.activity_read_article)

        val article: Article = intent.getSerializableExtra("articleData") as Article

        article_title.setText(article.getName())
        news_item_content_text_view.setText(article.getContent())
        Glide.with(this).load(article.getUrl()).into(avatar)

        comment_button.setOnClickListener {
            val fullSheetDialogFragment = ListBottomSheetDialogFragment()
            fullSheetDialogFragment.show(supportFragmentManager, "FullSheetDialogFragment")
        }
        like_button.setOnClickListener {

            Toast.makeText(getApplicationContext(), "已点赞", Toast.LENGTH_SHORT).show()
        }
        favorite_button.setOnClickListener {

            Toast.makeText(getApplicationContext(), "已收藏", Toast.LENGTH_SHORT).show()
        }
        font_button.setOnClickListener {
            val typeface: Typeface = Typeface.createFromAsset(assets, "fonts/Roboto-ThinItalic.ttf")
            this.findViewById<TextView>(R.id.news_item_content_text_view)?.setTypeface(typeface)
        }
    }
}
