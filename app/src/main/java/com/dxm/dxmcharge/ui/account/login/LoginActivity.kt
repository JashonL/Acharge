package com.dxm.dxmcharge.ui.account.login

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.databinding.ActivityLoginBinding
import com.dxm.dxmcharge.ui.account.register.RegisterActivity


class LoginActivity : AppCompatActivity() {

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)
        initviews()
        setlisteners()
    }

    private fun setlisteners() {
        bind.btRegister.setOnClickListener {

        }
    }

    private fun initviews() {

        bind.tvRegister.run {
            highlightColor = resources.getColor(android.R.color.transparent)
            movementMethod = LinkMovementMethod.getInstance()
            text = getTvSpan()
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
        val span = ForegroundColorSpan(ContextCompat.getColor(this,R.color.color_text_33))
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