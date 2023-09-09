package com.dxm.dxmcharge.ui.account.login

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.charge.lib.util.LanUtils
import com.charge.lib.util.MD5Util
import com.charge.lib.util.ToastUtil
import com.charge.lib.util.Util
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.base.BaseActivity
import com.dxm.dxmcharge.databinding.ActivityLoginBinding
import com.dxm.dxmcharge.ui.device.ChargeListActivity
import com.dxm.dxmcharge.ui.account.register.RegisterActivity
import com.shuoxd.lib.service.account.User


class LoginActivity : BaseActivity() {

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, LoginActivity::class.java))
        }

        fun startClearTask(context: Context?) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context?.startActivity(intent)
        }
    }


    private lateinit var bind: ActivityLoginBinding
    val logViewModel by lazy { ViewModelProvider(this)[LoginModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)
        initviews()
        initData()
        setlisteners()
    }

    private fun initData() {
        logViewModel.newLoginLiveData.observe(this) {
            dismissDialog()
            val orNull = it.getOrNull()
            if (orNull != null) {
                loginSuccess(orNull)
            } else {
                val message = it.exceptionOrNull()?.message
                showResultDialog(message, null, null)


            }
        }

    }


    private fun loginSuccess(user: User?) {
        user?.password = bind.etPassword.getText()
        accountService().saveUserInfo(user)
        ChargeListActivity.start(this)
        finish()

    }


    private fun setlisteners() {
        bind.btLogin.setOnClickListener {
            login()
        }


    }


    private fun login() {
        val userName = bind.etUsername.getText()
        val password = bind.etPassword.getText()
        when {
            TextUtils.isEmpty(userName) -> {
                ToastUtil.show(getString(R.string.m74_please_input_username))
            }
            TextUtils.isEmpty(password) -> {
                ToastUtil.show(getString(R.string.m76_password_cant_empty))
            }
            else -> {
                if (!TextUtils.isEmpty(password)) {
                    showDialog()
//                    val pwdMd5 = MD5Util.md5(password)
                    val version = Util.getVersion(this)
                    val deviceId = deviceService().getDeviceId()
                    val language = LanUtils.getLanguage(this)


                    logViewModel.login(
                        "login", userName, deviceId, password, version,
                        language.toString()
                    )
                }
            }
        }
    }


    private fun initviews() {

        bind.tvRegister.run {
            highlightColor = resources.getColor(android.R.color.transparent)
            movementMethod = LinkMovementMethod.getInstance()
            text = getTvSpan()
        }

        val user = accountService().user()
        val logout = accountService().isLogout()
        user?.let {
            val userId = it.userId
            val password = it.password
            bind.etUsername.setContent(userId)
            bind.etPassword.setContent(password)
            if (!logout) {
                login()
            }


        }


    }


    private fun getTvSpan(): SpannableString {
        val toSignUp = getString(R.string.to_sign_up)
        val signUp = getString(R.string.sign_up)
        val content = toSignUp + signUp
        return SpannableString(content).apply {
            addClickSpan(this, signUp) {
                RegisterActivity.start(this@LoginActivity)
            }
            addColorSpan(this, signUp)

        }
    }

    private fun addColorSpan(spannable: SpannableString, colorSpanContent: String) {
        val span = ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_text_33))
        val startPosition = spannable.toString().indexOf(colorSpanContent)
        val endPosition = startPosition + colorSpanContent.length
        spannable.setSpan(span, startPosition, endPosition, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    private fun addClickSpan(
        spannable: SpannableString,
        clickSpanContent: String,
        click: (View) -> Unit
    ) {
        val span = object : ClickableSpan() {
            override fun onClick(view: View) {
                click(view)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                //去掉下划线
                ds.isUnderlineText = false
            }
        }
        val startPosition = spannable.toString().indexOf(clickSpanContent)
        val endPosition = startPosition + clickSpanContent.length
        spannable.setSpan(span, startPosition, endPosition, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }


}