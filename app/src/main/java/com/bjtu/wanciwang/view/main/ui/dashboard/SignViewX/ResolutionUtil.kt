package com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX

import android.content.Context
import android.util.DisplayMetrics
import android.util.SparseArray
import android.view.WindowManager
import androidx.fragment.app.Fragment
import java.text.DecimalFormat

class ResolutionUtil {
    private var isInitial = false
    var screenWidth = 0
        private set
    var screenHeight = 0
        private set
    /**
     * 垂直方向数据列
     */
    private val verticalPixelList: SparseArray<Int>
    /**
     * 水平方向数据列
     */
    private val horizontalPixelList: SparseArray<Int>

    /**
     * 获取当前设备屏幕分辨率
     *
     * @param context context
     */
    fun init(context: Context) {
        if (!isInitial) {
            val metrics = DisplayMetrics()
            val windowManager =
                context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(metrics)
            screenWidth = metrics.widthPixels
            screenHeight = metrics.heightPixels
            isInitial = true
        }
    }

    fun formatHorizontal(standardValue: Int): Int {
        var processedValue = horizontalPixelList[standardValue, -1]
        if (processedValue == -1) {
            processedValue = Integer.valueOf(
                getFormatDouble(
                    "#0000",
                    standardValue * screenWidth / STANDARD_SCREEN_WIDTH.toDouble()
                )
            )
            horizontalPixelList.append(standardValue, processedValue)
        }
        return processedValue
    }

    fun formatVertical(standardValue: Int): Int {
        var processedValue = verticalPixelList[standardValue, -1]
        if (processedValue == -1) {
            processedValue = Integer.valueOf(
                getFormatDouble(
                    "#0000",
                    standardValue * screenHeight / STANDARD_SCREEN_HEIGHT.toDouble()
                )
            )
            verticalPixelList.append(standardValue, processedValue)
        }
        return processedValue
    }

    companion object {
        @JvmStatic
        @Volatile
        var instance: ResolutionUtil? = null
            get() {
                if (field == null) {
                    synchronized(ResolutionUtil::class.java) {
                        if (field == null) {
                            field = ResolutionUtil()
                        }
                    }
                }
                return field
            }
            private set
        /**
         * UI模版标准宽度
         */
        private const val STANDARD_SCREEN_WIDTH = 900
        /**
         * UI模版标准高度
         */
        private const val STANDARD_SCREEN_HEIGHT = 1600

        fun getFormatDouble(str: String?, sum: Double): String {
            val df = DecimalFormat(str)
            return df.format(sum)
        }
    }

    init {
        verticalPixelList = SparseArray()
        horizontalPixelList = SparseArray()
    }
}