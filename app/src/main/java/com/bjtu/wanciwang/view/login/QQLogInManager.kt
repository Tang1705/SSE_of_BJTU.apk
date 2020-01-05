package com.bjtu.wanciwang.view.login

import android.app.Activity
import android.content.Context
import android.content.Intent

import com.tencent.connect.UserInfo
import com.tencent.connect.auth.QQToken
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError

import org.json.JSONException
import org.json.JSONObject

class QQLogInManager
/**
 * 构造函数，包括app_id
 *
 * @param app_id
 * @param o
 */
    (app_id: String, o: Any) {

    private var app_id = ""

    private var mTencent: Tencent? = null

    private var mUserInfo: UserInfo? = null

    private var localLoginListener: LocalLoginListener? = null

    private val qqLoginListener: QQLoginListener

    private val mContext: Context

    private val mActivity: Activity


    init {

        this.app_id = app_id

        this.mContext = o as Context

        this.mActivity = o as Activity

        this.qqLoginListener = o as QQLoginListener

        initData()

    }


    /**
     * 初始化数据
     */

    private fun initData() {

        localLoginListener = LocalLoginListener()

        if (mTencent == null) {

            mTencent = Tencent.createInstance(app_id, mContext)

        }

    }


    /**
     * 回调结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    fun onActivityResultData(requestCode: Int, resultCode: Int, data: Intent?) {

        Tencent.onActivityResultData(requestCode, resultCode, data, localLoginListener)

    }


    /**
     * 启动QQ登录
     */

    fun launchQQLogin() {

        if (!mTencent!!.isSessionValid) {

            mTencent!!.login(mActivity, "all", localLoginListener)

        } else {

            mTencent!!.logout(mContext)

            launchQQLogin()

        }

    }


    /**
     * 退出QQ登录
     */

    fun logout() {

        if (mTencent!!.isSessionValid) {

            mTencent!!.logout(mActivity)

        }

    }


    /**
     * QQ登录状态监听器
     */

    interface QQLoginListener {

        fun onQQLoginSuccess(jsonObject: JSONObject, authInfo: UserAuthInfo)

        fun onQQLoginCancel()

        fun onQQLoginError(uiError: UiError)

    }


    /**
     * 本地QQ登录监听器
     */

    private inner class LocalLoginListener : IUiListener {


        private var openID: String? = null

        private var accessToken: String? = null

        private var expiresIn: String? = null


        override fun onComplete(o: Any) {

            initOpenIdAndToken(o)

            loadUserInfo()

        }


        override fun onError(uiError: UiError) {

            qqLoginListener.onQQLoginError(uiError)

        }


        override fun onCancel() {

            qqLoginListener.onQQLoginCancel()

        }


        /**
         * 初始化openID和access_token
         *
         * @param object
         */

        private fun initOpenIdAndToken(`object`: Any) {

            val jsonObject = `object` as JSONObject

            try {

                openID = jsonObject.getString("openid")

                accessToken = jsonObject.getString("access_token")

                expiresIn = jsonObject.getString("expires_in")

                mTencent!!.openId = openID

                mTencent!!.setAccessToken(accessToken, expiresIn)

            } catch (e: JSONException) {

                qqLoginListener.onQQLoginError(UiError(-99999, e.toString(), "初始化OpenId和Token失败"))

            }

        }


        /**
         * 加载用户信息
         */

        private fun loadUserInfo() {

            val qqToken = mTencent!!.qqToken

            mUserInfo = UserInfo(mContext, qqToken)

            mUserInfo!!.getUserInfo(object : IUiListener {

                /**
                 * 登录成功
                 * @param o
                 */

                override fun onComplete(o: Any) {

                    try {

                        val jsonObject = o as JSONObject

                        jsonObject.put("open_id", openID)

                        jsonObject.put("access_token", accessToken)

                        jsonObject.put("expires_in", expiresIn)

                        openID?.let { accessToken?.let { it1 -> expiresIn?.let { it2 ->
                            UserAuthInfo(it, it1,
                                it2
                            )
                        } } }?.let {
                            qqLoginListener.onQQLoginSuccess(
                                jsonObject,
                                it
                            )
                        }

                    } catch (e: JSONException) {

                        qqLoginListener.onQQLoginError(UiError(-99999, e.toString(), "获取OpenId异常"))

                    }

                }


                /**
                 * 登录出错
                 * @param uiError
                 */

                override fun onError(uiError: UiError) {

                    qqLoginListener.onQQLoginError(uiError)

                }


                /**
                 * 取消登录
                 *
                 */

                override fun onCancel() {

                    qqLoginListener.onQQLoginCancel()

                }

            })

        }

    }


    inner class UserAuthInfo(var openId: String, var accessToken: String, var expiresIn: String)

}