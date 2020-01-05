package com.bjtu.wanciwang.view.main.ui.dashboard

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.widget.AppCompatButton
import com.bjtu.wanciwang.R
import com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX.ResolutionUtil
import com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX.ResolutionUtil.Companion.instance
import com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX.StringUtil.getStringLength
import java.util.*

/**
 * SignDialogFragment
 */
class SignDialogFragment : AppCompatDialogFragment() {
    private var score = 0
    private val resolutionUtil: ResolutionUtil?
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val contentView =
            Objects.requireNonNull(activity)!!.layoutInflater
                .inflate(R.layout.fragment_dialog_sign, null)
        param
        initView(contentView)
        val appCompatDialog = AppCompatDialog(context, R.style.DialogNoTitle)
        appCompatDialog.setContentView(
            contentView, ViewGroup.LayoutParams(
                resolutionUtil!!.formatHorizontal(961),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        appCompatDialog.setCanceledOnTouchOutside(false)
        return appCompatDialog
    }

    private val param: Unit
        private get() {
            if (arguments != null) {
                score = arguments!!.getInt(ARG_SCORE_NUMBER, 0)
            }
        }

    @SuppressLint("RestrictedApi")
    private fun initView(root: View) {
        val tvMessage =
            root.findViewById<View>(R.id.dialog_sign_tv_message) as TextView
        val tvScoreNumber =
            root.findViewById<View>(R.id.dialog_sign_tv_score_number) as TextView
        val btnSure: AppCompatButton = root.findViewById(R.id.dialog_sign_btn_sure)
        val scoreLength = getStringLength(score)
        val numberStr =
            String.format(getString(R.string.much_score_int), score)
        val spannableString = SpannableString(numberStr)
        spannableString.setSpan(
            AbsoluteSizeSpan(resolutionUtil!!.formatVertical(120)),
            0,
            scoreLength,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannableString.setSpan(
            AbsoluteSizeSpan(resolutionUtil.formatVertical(39)),
            scoreLength,
            numberStr.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        tvScoreNumber.text = spannableString
        btnSure.supportBackgroundTintList =
            resources.getColorStateList(R.color.color_user_button_submit)
        btnSure.setOnClickListener {
            dialog!!.dismiss()
            if (onConfirmListener != null) {
                onConfirmListener!!.onConfirm()
            }
        }
        //------------------------------分辨率适配------------------------------------
        var layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.topMargin = resolutionUtil.formatVertical(67)
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL
        tvMessage.layoutParams = layoutParams
        tvMessage.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resolutionUtil.formatVertical(44).toFloat()
        )
        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.topMargin = resolutionUtil.formatVertical(39)
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL
        tvScoreNumber.layoutParams = layoutParams
        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.topMargin = resolutionUtil.formatVertical(61)
        layoutParams.bottomMargin = resolutionUtil.formatVertical(67)
        layoutParams.rightMargin = resolutionUtil.formatHorizontal(73)
        layoutParams.leftMargin = layoutParams.rightMargin
        btnSure.layoutParams = layoutParams
        btnSure.setTextSize(TypedValue.COMPLEX_UNIT_PX, resolutionUtil.formatVertical(48).toFloat())
    }

    private var onConfirmListener: OnConfirmListener? = null
    fun setOnConfirmListener(onConfirmListener: OnConfirmListener?) {
        this.onConfirmListener = onConfirmListener
    }

    interface OnConfirmListener {
        fun onConfirm()
    }

    companion object {
        const val TAG = "SIGN"
        const val ARG_SCORE_NUMBER = "ARG_SCORE_NUMBER"
        fun newInstance(score: Int): SignDialogFragment {
            val signDialogFragment = SignDialogFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SCORE_NUMBER, score)
            signDialogFragment.arguments = bundle
            return signDialogFragment
        }
    }

    init {
        resolutionUtil = instance
    }
}