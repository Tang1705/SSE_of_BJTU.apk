package com.bjtu.wanciwang.view.main.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.util.*
import android.view.MenuInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjtu.wanciwang.R
import com.bjtu.wanciwang.adapter.RecycleViewAdapter
import com.bjtu.wanciwang.common.GetByURL
import com.bjtu.wanciwang.entity.Article
import com.bjtu.wanciwang.view.article.scanarticleActivity
import com.bjtu.wanciwang.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    lateinit var recycleViewAdapter: RecycleViewAdapter
    var urls: ArrayList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        root!!.recyclerView.layoutManager = LinearLayoutManager(activity)
        setHasOptionsMenu(true)

        urls.add("http://tang5618.com:8080/elearn/materials/1/videoframe")
        urls.add("http://tang5618.com/WEB/org.png")
        urls.add("http://tang5618.com/WEB/zyd.jpg")
        urls.add("http://tang5618.com/WEB/teck.png")
        urls.add("http://tang5618.com/WEB/timeline.png")
        urls.add("tang5618.com/WEB/function.png")

        val list: ArrayList<Article> = ArrayList()

        // TODO
        val intent = activity!!.intent
        val bundle = intent.extras
        val uid: Int = bundle!!.getInt("uuid")
        val token: String = bundle.getString("token")!!

        val jsonObject =
            JSONObject(
                GetByURL.readParse(
                    "http://122.51.247.44:8080/atc/atclist?uuid=" + uid
                            + "&token=" + token
                )
            )

        if (jsonObject.getInt("code") == 0) {
            val jsonArray = jsonObject.getJSONArray("list")
            var i: Int = 0
            while (i < jsonArray.length()) {
                val j = jsonArray.getJSONObject(i)
                val article: Article = Article(
                    j.getString("name"), j.getInt("time").toString(),
                    j.getString("content"), urls[i]
                )
                list.add(article)
                i += 1
            }

        }

        root.article.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(activity, scanarticleActivity::class.java)
                startActivity(intent)
                activity!!.overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha)
            }

        })

        recycleViewAdapter = RecycleViewAdapter(list)
        root.recyclerView.adapter = recycleViewAdapter
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.bjtu.wanciwang.R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchManager =
            Objects.requireNonNull<FragmentActivity>(activity).getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        val searchMenuItem = menu.findItem(R.id.action_search)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
        searchView.queryHint = "探索更大的世界..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length <= 2) {
                    Toast.makeText(activity, "请输入不少于两个字符！", Toast.LENGTH_SHORT)
                        .show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        searchMenuItem.icon.setVisible(false, false)
    }


}