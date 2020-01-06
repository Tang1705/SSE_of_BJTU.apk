package com.bjtu.wanciwang.view.main.ui.home

import android.app.SearchManager
import android.content.Context
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
import com.bjtu.wanciwang.entity.Article
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlin.collections.ArrayList

class WordSelectFragment : Fragment() {
    lateinit var recycleViewAdapter: RecycleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_words_book, container, false)
        root!!.recyclerView.layoutManager = LinearLayoutManager(activity)
        setHasOptionsMenu(true)

        val list: ArrayList<Article> = ArrayList()

        // TODO
        list.add(
            Article(
                "Best Performance Solo",
                "EASY | 2020/1/4",
                "test",
                "http://47.94.107.165:8080/elearn/materials/2/media"
            )
        )

        list.add(
            Article(
                "Better Performance Solo",
                "EASY | 2020/1/4",
                "test",
                "http://47.94.107.165:8080/elearn/materials/2/media"
            )
        )

        list.add(
            Article(
                "Worst Performance Solo",
                "EASY | 2020/1/4",
                "test",
                "http://47.94.107.165:8080/elearn/materials/2/media"
            )
        )

        list.add(
            Article(
                "Worst Performance Solo",
                "EASY | 2020/1/4",
                "test",
                "http://47.94.107.165:8080/elearn/materials/2/media"
            )
        )

        list.add(
            Article(
                "Worst Performance Solo",
                "EASY | 2020/1/4",
                "test",
                "http://47.94.107.165:8080/elearn/materials/2/media"
            )
        )

        recycleViewAdapter = RecycleViewAdapter(list,this.activity,this,0)
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