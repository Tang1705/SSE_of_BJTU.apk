package com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX

import com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX.SignView.DayType

abstract class CalendarAdapter {
    abstract fun getType(dayOfMonth: Int): DayType?
}