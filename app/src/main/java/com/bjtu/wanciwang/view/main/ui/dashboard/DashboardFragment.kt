package com.bjtu.wanciwang.view.main.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bjtu.wanciwang.R
import com.bjtu.wanciwang.view.main.ui.dashboard.SignDialogFragment.OnConfirmListener
import com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX.SignAdapter
import com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX.SignEntity
import com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX.SignView
import com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX.SignView.OnTodayClickListener
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import java.util.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.activity_main_cv as activity_main_cv1


@Suppress("PLUGIN_WARNING")
class DashboardFragment : Fragment() {

    lateinit var root: View;
    private var data: List<SignEntity>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        initData()

        return root
    }

    private fun initData() {
        root.activity_main_cv1?.setOnTodayClickListener(onTodayClickListener)
        onReady()
        root.l_sign!!.setOnClickListener { onSign() }
    }

    @SuppressLint("SetTextI18n")
    private fun onReady() {
        val calendar = Calendar.getInstance()
        val month = calendar[Calendar.MONTH]
        root.tv_sign_data!!.text = "您已经连续签到2天"
        root.tv_score!!.text = 305.toString()
        root.tv_date!!.text =
            calendar[Calendar.YEAR].toString() + "-" + (calendar[Calendar.MONTH] + 1).toString()
        val calendarToday = Calendar.getInstance()
        val dayOfMonthToday = calendarToday[Calendar.DAY_OF_MONTH]
        data = ArrayList()
        //已经签到
        val integers: MutableList<Int> = ArrayList()
        integers.add(1)
        integers.add(2)
        //奖品设置
        val integersX: MutableList<Int> = ArrayList()
        integersX.add(5)
        integersX.add(10)
        for (i in 0..29) {
            val signEntity = SignEntity()
            if (dayOfMonthToday == i + 1) {
                signEntity.dayType = 2
            } else {
                if (integersX.contains(i)) {
                    signEntity.dayType = 4
                } else {
                    signEntity.dayType = if (integers.contains(i + 1)) 0 else 1
                }
                (data as ArrayList<SignEntity>).add(signEntity)
            }
        }
        val signAdapter = SignAdapter(data as ArrayList<SignEntity>)
        root.activity_main_cv1!!.setAdapter(signAdapter)
    }


    private fun onSign() {
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val signDialogFragment = SignDialogFragment.newInstance(15)
        signDialogFragment.setOnConfirmListener(onConfirmListener)
        signDialogFragment.show(fragmentManager, SignDialogFragment.TAG)
    }

    private fun signToday() {
        data!![this.root.activity_main_cv1!!.dayOfMonthToday - 1].dayType =
            SignView.DayType.SIGNED.value
        root.activity_main_cv1!!.notifyDataSetChanged()
        root.l_sign!!.isEnabled = false
        root.tv_sign_state!!.setText(R.string.have_signed)
        val score = Integer.valueOf((root.tv_score!!.text as String))
        root.tv_score!!.text = (score + 15).toString()
    }

    private val onTodayClickListener: OnTodayClickListener =
        object : OnTodayClickListener {
            override fun onTodayClick() {
                onSign()
            }
        }


    private val onConfirmListener: OnConfirmListener =
        object : OnConfirmListener {
            override fun onConfirm() {
                signToday()
            }
        }

}