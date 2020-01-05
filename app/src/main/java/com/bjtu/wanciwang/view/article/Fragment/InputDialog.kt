package com.bjtu.wanciwang.view.article.Fragment

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
//import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.getAttributes

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.NonNull
import com.bjtu.wanciwang.R


/**
 * @Author：淘跑
 * @Date: 2018/11/14 17:20
 * @Use：
 */
class InputDialog(context: Context) : Dialog(context) {

    lateinit var mContext: Context
    lateinit var mRootView: View

    init {
        //        super(context, R.style.CustomDialog);
        init(context)
    }

    fun init(context: Context) {
        mContext = context
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_input, null)
        setContentView(mRootView)
        val window = getWindow()
        val params = window?.getAttributes()
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        window?.setAttributes(params)
        window?.setGravity(Gravity.BOTTOM)
    }

}