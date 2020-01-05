@file:Suppress("DEPRECATION")

package com.bjtu.wanciwang.view.main.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bjtu.wanciwang.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.fragment_notifications.view.*


@Suppress("DEPRECATION")
class NotificationsFragment : Fragment() {
    private var blurImageView: ImageView? = null
    private var avatarImageView: ImageView? = null
    private var mContext: Context? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root =
            inflater.inflate(R.layout.fragment_notifications, container, false)
        blurImageView =
            root.findViewById<View>(R.id.iv_blur) as ImageView
        avatarImageView =
            root.findViewById<View>(R.id.iv_avatar) as ImageView
        Glide.with(activity!!).load(R.drawable.head).transform(BlurTransformation(25), CenterCrop())
            .into(blurImageView!!)

        Glide.with(activity!!).load(R.drawable.head).transform(CropCircleTransformation())
            .into(avatarImageView!!)

        val intent = activity!!.intent
        val bundle = intent.extras
        if (bundle != null) {
            root.user_name.text = bundle.getString("user")
        }

        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context //mContext 是成员变量，上下文引用
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }
}