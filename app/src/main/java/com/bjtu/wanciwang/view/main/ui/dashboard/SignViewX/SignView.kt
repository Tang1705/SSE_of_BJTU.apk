package com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.bjtu.wanciwang.R
import java.util.*

/**
 * 签到日历控件
 * Created by Xia_焱 on  2019/9/28.
 */
class SignView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) :
    View(context, attrs, defStyleAttr) {
    var dayOfMonthToday = 0
        private set
    private var markerTextY = 0
    private var verticalCellTop = 0
    private var sumDayOfMonth = 0
    private var daysOfFirstWeek = 0
    private var horizontalSpace = 0
    private var deltaTextCellY = 0
    private var deltaTextMarkerY = 0
    private var verticalSpace = 0
    private var verticalMargin = 0
    private var horizontalMargin = 0
    private var cellSize = 0
    private var waitLineSize = 0
    private var waitPath: Path? = null
    private var waitRect: Rect? = null
    private var paintWeekday: Paint? = null
    private var paintWeekend: Paint? = null
    private var paintTextNormal: Paint? = null
    private var paintTextHighlight: Paint? = null
    private var paintBackgroundWait: Paint? = null
    private var paintBackgroundNormal: Paint? = null
    private var paintBackgroundHighlight: Paint? = null
    private var adapter: CalendarAdapter? = null
    private var resolutionUtil: ResolutionUtil? = null
    private var paintReward: Paint? = null
    private var paintRewardX: Paint? = null
    private fun initResolution() {
        resolutionUtil = ResolutionUtil.instance
        verticalSpace = resolutionUtil!!.formatVertical(VERTICAL_SPACE)
        verticalMargin = resolutionUtil!!.formatVertical(VERTICAL_MARGIN)
        horizontalMargin = resolutionUtil!!.formatHorizontal(HORIZONTAL_MARGIN)
        cellSize = resolutionUtil!!.formatVertical(CELL_SIZE)
        waitLineSize = resolutionUtil!!.formatVertical(WAIT_LINE_SIZE)
    }

    private fun initPaint() {
        val markerTextSize: Int = resolutionUtil!!.formatVertical(MARKER_TEXT_SIZE)
        val cellTextSize: Int = resolutionUtil!!.formatVertical(CELL_TEXT_SIZE)
        paintWeekday = Paint()
        paintWeekday!!.isAntiAlias = true
        paintWeekday!!.color = COLOR_MARKER_WEEKDAY
        paintWeekday!!.textSize = markerTextSize.toFloat()
        paintWeekday!!.textAlign = Paint.Align.CENTER
        paintWeekend = Paint()
        paintWeekend!!.isAntiAlias = true
        paintWeekend!!.color = COLOR_MARKER_WEEKEND
        paintWeekend!!.textSize = markerTextSize.toFloat()
        paintWeekend!!.textAlign = Paint.Align.CENTER
        paintTextNormal = Paint()
        paintTextNormal!!.isAntiAlias = true
        paintTextNormal!!.color = COLOR_TEXT_NORMAL
        paintTextNormal!!.textSize = cellTextSize.toFloat()
        paintTextNormal!!.textAlign = Paint.Align.CENTER
        //签到画笔颜色
        paintTextHighlight = Paint()
        paintTextHighlight!!.isAntiAlias = true
        paintTextHighlight!!.alpha = 0 //透明度
        paintTextHighlight!!.textSize = cellTextSize.toFloat()
        paintTextHighlight!!.textAlign = Paint.Align.CENTER
        paintBackgroundWait = Paint()
        paintBackgroundWait!!.isAntiAlias = true
        paintBackgroundWait!!.color = COLOR_BACKGROUND_WAIT
        paintBackgroundWait!!.strokeWidth = 2f
        paintBackgroundWait!!.style = Paint.Style.STROKE
        //未签到 背景
        paintBackgroundNormal = Paint()
        paintBackgroundNormal!!.isAntiAlias = true
        paintBackgroundNormal!!.color = COLOR_BACKGROUND_NORMAL
        paintBackgroundNormal!!.strokeWidth = 2f
        paintBackgroundNormal!!.style = Paint.Style.FILL
        //已签到 背景
        paintBackgroundHighlight = Paint()
        paintBackgroundHighlight!!.isAntiAlias = true
        // paintBackgroundHighlight.setColor(COLOR_BACKGROUND_HIGHLIGHT);
        paintBackgroundHighlight!!.strokeWidth = 2f
        paintBackgroundHighlight!!.style = Paint.Style.FILL
        //奖项
        paintReward = Paint()
        paintReward!!.isAntiAlias = true
        paintReward!!.strokeWidth = 2f
        paintReward!!.style = Paint.Style.FILL
        paintRewardX = Paint()
        paintRewardX!!.isAntiAlias = true
        paintRewardX!!.alpha = 0 //透明度
        paintRewardX!!.textSize = cellTextSize.toFloat()
        paintRewardX!!.textAlign = Paint.Align.CENTER
    }

    private fun initData() {
        val fmiMarker = paintWeekday!!.fontMetricsInt
        deltaTextMarkerY = -(fmiMarker.bottom - fmiMarker.top) / 2 - fmiMarker.top
        markerTextY = verticalMargin + cellSize / 2
        val fmiCell = paintTextNormal!!.fontMetricsInt
        deltaTextCellY = -(fmiCell.bottom - fmiCell.top) / 2 - fmiCell.top
        verticalCellTop = verticalMargin + cellSize
        val calendarToday = Calendar.getInstance()
        dayOfMonthToday = calendarToday[Calendar.DAY_OF_MONTH]
        var dayOfWeek: Int
        sumDayOfMonth = calendarToday.getActualMaximum(Calendar.DAY_OF_MONTH)
        val calendarFirstDay = Calendar.getInstance()
        calendarFirstDay[Calendar.DAY_OF_MONTH] = 1
        dayOfWeek = calendarFirstDay[Calendar.DAY_OF_WEEK]
        dayOfWeek = if (dayOfWeek == Calendar.SUNDAY) {
            7
        } else {
            dayOfWeek - 1
        }
        daysOfFirstWeek = MAX_COLUMN - dayOfWeek + 1
    }

    private fun createWaitBackground(topX: Int, topY: Int) {
        waitPath = Path()
        waitPath!!.moveTo(topX.toFloat(), topY + waitLineSize.toFloat())
        waitPath!!.lineTo(topX.toFloat(), topY.toFloat())
        waitPath!!.lineTo(topX + waitLineSize.toFloat(), topY.toFloat())
        waitPath!!.moveTo(topX + cellSize - waitLineSize.toFloat(), topY + cellSize.toFloat())
        waitPath!!.lineTo(topX + cellSize.toFloat(), topY + cellSize.toFloat())
        waitPath!!.lineTo(topX + cellSize.toFloat(), topY + cellSize - waitLineSize.toFloat())
        waitRect = Rect(topX, topY, topX + cellSize, topY + cellSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        horizontalSpace =
            (w - MAX_COLUMN * cellSize - horizontalMargin * 2) / (MAX_COLUMN - 1)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        drawWeekMark(canvas)
        drawCellsBackground(canvas)
        drawCells(canvas)
    }

    private fun drawWeekMark(canvas: Canvas) {
        val y = markerTextY + deltaTextMarkerY
        for (i in 0..6) {
            val x = horizontalMargin + i * (horizontalSpace + cellSize) + cellSize / 2
            if (i < 5) {
                canvas.drawText(
                    WEEK_MARK[i],
                    x.toFloat(),
                    y.toFloat(),
                    paintWeekday!!
                )
            } else {
                canvas.drawText(
                    WEEK_MARK[i],
                    x.toFloat(),
                    y.toFloat(),
                    paintWeekend!!
                )
            }
        }
    }

    private fun drawCellsBackground(canvas: Canvas) {
        for (i in 1..dayOfMonthToday) {
            drawCellBackground(canvas, i, getColumnIndex(i), getRowIndex(i))
        }
    }

    /**
     * 根据行列序号绘制日期背景
     *
     * @param canvas     画布
     * @param dayOfMonth 日期
     * @param column     列序号
     * @param row        行序号
     */
    private fun drawCellBackground(
        canvas: Canvas,
        dayOfMonth: Int,
        column: Int,
        row: Int
    ) {
        val x = horizontalMargin + column * (horizontalSpace + cellSize) + cellSize / 2
        val y =
            verticalCellTop + verticalSpace * (row + 1) + cellSize * row + cellSize / 2
        if (adapter != null) {
            val dayType: DayType = adapter!!.getType(dayOfMonth)!!
            when (dayType) {
                DayType.WAITING -> {
                    if (waitPath == null) {
                        createWaitBackground(x - cellSize / 2, y - cellSize / 2)
                    }
                    canvas.drawPath(waitPath!!, paintBackgroundWait!!)
                }
                DayType.SIGNED -> canvas.drawBitmap(
                    BitmapFactory.decodeResource(
                        resources,
                        R.mipmap.icon_m
                    ), x - 30.toFloat(), y - 30.toFloat(), paintBackgroundHighlight
                )
                DayType.REWARD -> canvas.drawBitmap(
                    BitmapFactory.decodeResource(
                        resources,
                        R.mipmap.icon_j
                    ), x - 30.toFloat(), y - 30.toFloat(), paintReward
                )
                else -> canvas.drawCircle(
                    x.toFloat(),
                    y.toFloat(),
                    cellSize / 2.toFloat(),
                    paintBackgroundNormal!!
                )
            }
        } else {
            canvas.drawCircle(
                x.toFloat(),
                y.toFloat(),
                cellSize / 2.toFloat(),
                paintBackgroundNormal!!
            )
        }
    }

    private fun drawCells(canvas: Canvas) {
        for (i in 1..sumDayOfMonth) {
            drawCell(canvas, i, getColumnIndex(i), getRowIndex(i))
        }
    }

    /**
     * 根据行列序号绘制日期
     *
     * @param canvas     画布
     * @param dayOfMonth 日期
     * @param column     列序号
     * @param row        行序号
     */
    private fun drawCell(
        canvas: Canvas,
        dayOfMonth: Int,
        column: Int,
        row: Int
    ) {
        val x = horizontalMargin + column * (horizontalSpace + cellSize) + cellSize / 2
        val y =
            verticalCellTop + verticalSpace * (row + 1) + cellSize * row + cellSize / 2 + deltaTextCellY
        if (adapter != null && dayOfMonth <= dayOfMonthToday) {
            val dayType: DayType = adapter!!.getType(dayOfMonth)!!
            val paint: Paint?
            paint = when (dayType) {
                DayType.SIGNED -> paintTextHighlight
                DayType.REWARD -> paintRewardX
                else -> paintTextNormal
            }
            canvas.drawText(dayOfMonth.toString(), x.toFloat(), y.toFloat(), paint!!)
        } else {
            canvas.drawText(dayOfMonth.toString(), x.toFloat(), y.toFloat(), paintTextNormal!!)
        }
    }

    /**
     * 获取列序号
     *
     * @param dayOfMonth 日期
     * @return 列序号
     */
    private fun getColumnIndex(dayOfMonth: Int): Int {
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        var dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
        dayOfWeek = if (dayOfWeek == Calendar.SUNDAY) {
            6
        } else {
            dayOfWeek - 2
        }
        return dayOfWeek
    }

    /**
     * 获取行序号
     *
     * @param dayOfMonth 日期
     * @return 行序号
     */
    private fun getRowIndex(dayOfMonth: Int): Int {
        val weight =
            (dayOfMonth - daysOfFirstWeek) / (MAX_COLUMN * 1f)
        val rowIndexDouble =
            Math.abs(Math.ceil(weight.toDouble()))
        return rowIndexDouble.toInt()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            val x = event.x
            val y = event.y
            if (waitPath != null) {
                if (adapter!!.getType(dayOfMonthToday)!!.equals(DayType.WAITING)) {
                    if (x >= waitRect!!.left && y >= waitRect!!.top && x <= waitRect!!.right && y <= waitRect!!.bottom) {
                        if (onTodayClickListener != null) {
                            onTodayClickListener!!.onTodayClick()
                        }
                    }
                }
            }
        }
        return true
    }

    fun setAdapter(adapter: CalendarAdapter?) {
        this.adapter = adapter
        this.invalidate()
    }

    fun notifyDataSetChanged() {
        invalidate()
    }

    private var onTodayClickListener: OnTodayClickListener? = null
    fun setOnTodayClickListener(onTodayClickListener: OnTodayClickListener?) {
        this.onTodayClickListener = onTodayClickListener
    }

    interface OnTodayClickListener {
        fun onTodayClick()
    }

    enum class DayType(val value: Int) {
        /**
         * 已签到状态，时间已过
         */
        SIGNED(0),
        /**
         * 未签到状态，时间已过
         */
        UNSIGNED(1),
        /**
         * 等待状态，即当日还未签到
         */
        WAITING(2),
        /**
         * 不可达到状态，未到时间
         */
        UNREACHABLE(3),
        /**
         * 不可用状态，非当前月份
         */
        DISABLED(5),
        /**
         * 奖赏
         */
        REWARD(4);

        companion object {
            fun valueOf(value: Int): DayType {
                return when (value) {
                    0 -> SIGNED
                    1 -> UNSIGNED
                    2 -> WAITING
                    3 -> UNREACHABLE
                    4 -> REWARD
                    5 -> DISABLED
                    else -> DISABLED
                }
            }
        }

    }

    companion object {
        private val WEEK_MARK =
            arrayOf("一", "二", "三", "四", "五", "六", "日")
        private const val MAX_COLUMN = 7
        /**
         * 周内
         */
        private const val COLOR_MARKER_WEEKDAY = -0x666667
        private const val COLOR_MARKER_WEEKEND = -0xe47633
        /**
         * 已签到背景色
         */
        private const val COLOR_BACKGROUND_HIGHLIGHT = -0xe47633
        /**
         * 未签到背景色
         */
        private const val COLOR_BACKGROUND_NORMAL = -0x1
        /**
         * 等待签到背景色
         */
        private const val COLOR_BACKGROUND_WAIT = -0x18b8f
        /**
         * 已签到文字颜色
         */
        private const val COLOR_TEXT_HIGHLIGHT = -0x1
        /**
         * 未签到文字颜色
         */
        private const val COLOR_TEXT_NORMAL = -0x9f9fa0
        //    /**
//     * 不可用文字颜色
//     */
//    private static final int COLOR_TEXT_DISABLED = 0xFFD4D4D4;
        private const val MARKER_TEXT_SIZE = 40
        private const val CELL_TEXT_SIZE = 40
        private const val VERTICAL_SPACE = 41
        private const val VERTICAL_MARGIN = 52
        private const val HORIZONTAL_MARGIN = 29
        private const val CELL_SIZE = 70
        private const val WAIT_LINE_SIZE = 14
    }

    init {
        initResolution()
        initPaint()
        initData()
    }
}