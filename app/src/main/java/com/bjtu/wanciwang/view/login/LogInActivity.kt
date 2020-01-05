package com.bjtu.wanciwang.view.login

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bjtu.wanciwang.R
import com.bjtu.wanciwang.common.GetByURL.readParse
import com.bjtu.wanciwang.view.main.MainActivity
import com.tencent.tauth.UiError
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class LogInActivity : AppCompatActivity(), QQLogInManager.QQLoginListener {

    lateinit var qqLogInManager: QQLogInManager;

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        sign_in.setOnClickListener {
            if (username.text.isEmpty()) {
                val dialog: AlertDialog = AlertDialog.Builder(
                    this@LogInActivity
                )
                    .setIcon(R.drawable.ic_warning)
                    .setTitle("警告")
                    .setMessage("电话号码不能为空，请填写电话号码！")
                    .setPositiveButton(
                        "确认"
                    ) { dialog, _ -> dialog.dismiss() }
                    .create()
                dialog.show()
                val typeface: Typeface = Typeface.createFromAsset(assets, "fonts/mine.ttf")
                dialog.findViewById<TextView>(android.R.id.message)?.setTypeface(typeface)
            } else if (password.text.isEmpty()) {
                val dialog: AlertDialog = AlertDialog.Builder(
                    this@LogInActivity
                )
                    .setIcon(R.drawable.ic_warning)
                    .setTitle("警告")
                    .setMessage("密码不能为空，请填写密码！")
                    .setPositiveButton(
                        "确认"
                    ) { dialog, _ -> dialog.dismiss() }
                    .create()
                dialog.show()
                val typeface: Typeface = Typeface.createFromAsset(assets, "fonts/mine.ttf")
                dialog.findViewById<TextView>(android.R.id.message)?.setTypeface(typeface)
            } else {
                progress.visibility = View.VISIBLE
                progress.setOnClickListener(null)
                val jsonObject =
                    JSONObject(
                        readParse(
                            "http://122.51.247.44:8080/user/login?type=normal&uid=" +
                                    username.text + "&pwd=" + password.text
                        )
                    )
                val code: Int = jsonObject.getInt("code")
                val intent = Intent(this@LogInActivity, MainActivity::class.java)

                if (code == 0) {
                    startActivity(intent)
                    overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha)
                    finish()
                } else {
                    progress.visibility = View.INVISIBLE
                    val dialog: AlertDialog = AlertDialog.Builder(
                        this@LogInActivity
                    )
                        .setIcon(R.drawable.ic_error)
                        .setTitle("错误")
                        .setMessage("用户名或密码错误，请重试！")
                        .setPositiveButton(
                            "确认"
                        ) { dialog, _ -> dialog.dismiss() }
                        .create()
                    dialog.show()
                    val typeface: Typeface = Typeface.createFromAsset(assets, "fonts/mine.ttf")
                    dialog.findViewById<TextView>(android.R.id.message)?.setTypeface(typeface)

                }
            }

        }

        log_in.setOnClickListener {
            if (username.text.isEmpty()) {
                val dialog: AlertDialog = AlertDialog.Builder(
                    this@LogInActivity
                )
                    .setIcon(R.drawable.ic_warning)
                    .setTitle("警告")
                    .setMessage("电话号码不能为空，请填写电话号码！")
                    .setPositiveButton(
                        "确认"
                    ) { dialog, _ -> dialog.dismiss() }
                    .create()
                dialog.show()
                val typeface: Typeface = Typeface.createFromAsset(assets, "fonts/mine.ttf")
                dialog.findViewById<TextView>(android.R.id.message)?.setTypeface(typeface)
            } else if (password.text.isEmpty()) {
                val dialog: AlertDialog = AlertDialog.Builder(
                    this@LogInActivity
                )
                    .setIcon(R.drawable.ic_warning)
                    .setTitle("警告")
                    .setMessage("密码不能为空，请填写密码！")
                    .setPositiveButton(
                        "确认"
                    ) { dialog, _ -> dialog.dismiss() }
                    .create()
                dialog.show()
                val typeface: Typeface = Typeface.createFromAsset(assets, "fonts/mine.ttf")
                dialog.findViewById<TextView>(android.R.id.message)?.setTypeface(typeface)
            } else {
                progress.visibility = View.VISIBLE
                progress.setOnClickListener(null)
                val jsonObject =
                    JSONObject(
                        readParse(
                            "http://122.51.247.44:8080/user/reg?uid=" +
                                    username.text + "&pwd=" + password.text
                        )
                    )
                val code: Int = jsonObject.getInt("code")
                val intent = Intent(this@LogInActivity, MainActivity::class.java)

                if (code == 0) {
                    progress.visibility = View.INVISIBLE
                    val dialog: AlertDialog = AlertDialog.Builder(
                        this@LogInActivity
                    )
                        .setIcon(R.drawable.ic_success)
                        .setTitle("成功")
                        .setMessage("恭喜您，注册成功！")
                        .setPositiveButton(
                            "确认"
                        ) { dialog, _ -> dialog.dismiss() }
                        .create()
                    dialog.show()
                    val typeface: Typeface = Typeface.createFromAsset(assets, "fonts/mine.ttf")
                    dialog.findViewById<TextView>(android.R.id.message)?.setTypeface(typeface)
                } else {
                    progress.visibility = View.INVISIBLE
                    val dialog: AlertDialog = AlertDialog.Builder(
                        this@LogInActivity
                    )
                        .setIcon(R.drawable.ic_error)
                        .setTitle("错误")
                        .setMessage("注册失败，请稍后重试！")
                        .setPositiveButton(
                            "确认"
                        ) { dialog, _ -> dialog.dismiss() }
                        .create()
                    dialog.show()
                    val typeface: Typeface = Typeface.createFromAsset(assets, "fonts/mine.ttf")
                    dialog.findViewById<TextView>(android.R.id.message)?.setTypeface(typeface)
                }
            }

        }



        wechat.setOnClickListener {
            Toast.makeText(
                this@LogInActivity,
                "功能马上开放，敬请期待！",
                Toast.LENGTH_SHORT
            ).show();
        }

        weibo.setOnClickListener {
            Toast.makeText(
                this@LogInActivity,
                "功能马上开放，敬请期待！",
                Toast.LENGTH_SHORT
            ).show();
        }

        qqLogInManager = QQLogInManager("101844536", this);
        qq.setOnClickListener {
            qqLogInManager.launchQQLogin()
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        // 回调
        qqLogInManager.onActivityResultData(requestCode, resultCode, data)
    }

    override fun onQQLoginSuccess(jsonObject: JSONObject, authInfo: QQLogInManager.UserAuthInfo) {
        val intent = Intent(this@LogInActivity, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha)
        finish()
    }

    override fun onQQLoginError(uiError: UiError) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onQQLoginCancel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
