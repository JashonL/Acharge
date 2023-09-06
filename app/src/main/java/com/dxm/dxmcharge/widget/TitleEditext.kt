package com.dxm.dxmcharge.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.databinding.TitleEditBinding
import com.dxm.dxmcharge.extend.gone
import com.dxm.dxmcharge.extend.visible


class TitleEditext @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private val binding: TitleEditBinding

    private var title: String = ""
    private var hint: String = ""


    private var isPasswrod: Boolean

    private var rightShow: Boolean
    private var rightIcon: Drawable?


    private var onRightClickListeners: OnRightClickListeners? = null


    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.title_edit, this)
        binding = TitleEditBinding.bind(inflate)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TitleEditext,
            0, 0
        ).apply {
            try {
                title = getString(R.styleable.TitleEditext_TitleEditext_title) ?: ""
                hint = getString(R.styleable.TitleEditext_hint) ?: ""
                rightShow = getBoolean(R.styleable.TitleEditext_right_show, false)
                rightIcon = getDrawable(R.styleable.TitleEditext_titletext_right_icon)
                isPasswrod =
                    getBoolean(R.styleable.TitleEditext_ispassword, false)
            } finally {
                recycle()
            }
        }


        initViews()

    }


    private fun initViews() {
        setTitle(title)
        setHint(hint)

        //密码
        if (isPasswrod) {
            binding.etContent.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        if (rightShow) binding.ivRightIcon.visible() else binding.ivRightIcon.gone()


        rightIcon?.let {
            binding.ivRightIcon.setImageDrawable(it)
        }



        binding.ivRightIcon.setOnClickListener {
            onRightClickListeners?.onRightIconclick()
        }


    }


    fun setTitle(title: String?) {
        if (!title.isNullOrEmpty()) {
            binding.tvTitle.text = title
        }
    }

    fun setContent(content: String?) {
        if (!content.isNullOrEmpty()) {
            binding.etContent.setText(content)
            binding.etContent.setSelection(content.length)

        }
    }


    fun getText(): String {
        return binding.etContent.text.toString().trim()
    }


    fun setHint(hint: String?) {
        if (!hint.isNullOrEmpty()) {
            binding.etContent.hint = hint
        }
    }

    fun setEye() {
        if (isPasswrod) {
            binding.ivRightIcon.setImageResource(R.drawable.eye_close)

            binding.etContent.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            binding.ivRightIcon.setImageResource(R.drawable.eye_open)

            binding.etContent.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        this.isPasswrod = !isPasswrod;

    }


    fun setOnRightClick(onRightIconClick: OnRightClickListeners) {
        this.onRightClickListeners = onRightIconClick
    }

    interface OnRightClickListeners {
        fun onRightIconclick()
    }

}


