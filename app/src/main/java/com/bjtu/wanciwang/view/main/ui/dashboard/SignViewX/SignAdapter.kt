package com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX

import com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX.SignView.DayType
import com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX.SignView.DayType.Companion.valueOf

class SignAdapter(private val data: List<SignEntity>) : CalendarAdapter() {
    override fun getType(dayOfMonth: Int): DayType? {
        return valueOf(data[dayOfMonth - 1].dayType)
    }

}