package com.bjtu.wanciwang.view.article.Fragment

import android.view.WindowManager
import android.app.Dialog
import android.content.Context
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
//import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.getAttributes
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.bjtu.wanciwang.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_item_list_dialog.view.*


/**
 * @Author： 淘跑
 * @Date: 2018/7/2 15:18
 * @Use：
 */
class ListBottomSheetDialogFragment : BottomSheetDialogFragment() {
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //给dialog设置主题为透明背景 不然会有默认的白色背景
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomBottomSheetDialogTheme)
    }

    /**
     * 如果想要点击外部消失的话 重写此方法
     *
     * @param savedInstanceState
     * @return
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        //设置点击外部可消失
        dialog.setCanceledOnTouchOutside(true)
        //设置使软键盘弹出的时候dialog不会被顶起
        val win = dialog.window
        val params = win?.getAttributes()
        win?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        //这里设置dialog的进出动画
        win?.setWindowAnimations(R.style.DialogBottomAnim)
        return dialog
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        // 在这里将view的高度设置为精确高度，即可屏蔽向上滑动不占全屏的手势。
        //如果不设置高度的话 会默认向上滑动时dialog覆盖全屏
        val view = inflater.inflate(R.layout.fragment_item_list_dialog, container, false)
        view.setLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                getScreenHeight(
                    activity!!
                ) / 2
            )
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById(R.id.rv) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ItemAdapter(100)
        view.tv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                //弹出评论输入框
                val inputDialog = activity?.let {
                    InputDialog(
                        it
                    )
                }
                val window = inputDialog?.getWindow()
                val params = window?.getAttributes()
                //设置软键盘通常是可见的
                window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
                inputDialog?.show()
            }
        })
    }

    private inner class ItemAdapter internal constructor(private val mItemCount: Int) :
        RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.text.text = position.toString()
        }

        override fun getItemCount(): Int {
            return mItemCount
        }

        inner class MyViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(
                inflater.inflate(
                    R.layout.fragment_item_list_dialog_item,
                    parent,
                    false
                )
            ) {
            internal val text: TextView

            init {
                text = itemView.findViewById(R.id.text) as TextView
            }
        }

    }

    companion object {

        /**
         * 得到屏幕的高
         *
         * @param context
         * @return
         */
        fun getScreenHeight(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            return wm.defaultDisplay.height
        }
    }

}