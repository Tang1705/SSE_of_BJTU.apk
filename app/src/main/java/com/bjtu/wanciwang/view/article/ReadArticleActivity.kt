package com.bjtu.wanciwang.view.article

import android.content.Context
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spanned
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bjtu.wanciwang.R
import com.bjtu.wanciwang.view.article.Fragment.ListBottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_read_article.*


class ReadArticleActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.bjtu.wanciwang.R.layout.activity_read_article)
        comment_button.setOnClickListener{
            val fullSheetDialogFragment = ListBottomSheetDialogFragment()
            fullSheetDialogFragment.show(supportFragmentManager, "FullSheetDialogFragment")
        }
        like_button.setOnClickListener{

            Toast.makeText(getApplicationContext(), "已点赞", Toast.LENGTH_SHORT).show()
        }
        favorite_button.setOnClickListener{

            Toast.makeText(getApplicationContext(), "已收藏", Toast.LENGTH_SHORT).show()
        }
        font_button.setOnClickListener{
            val typeface: Typeface = Typeface.createFromAsset(assets, "fonts/Roboto-ThinItalic.ttf")
            this.findViewById<TextView>(R.id.news_item_content_text_view)?.setTypeface(typeface)
        }
    }
}
