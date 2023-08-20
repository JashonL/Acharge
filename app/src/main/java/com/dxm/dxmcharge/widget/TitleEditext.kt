package com.dxm.dxmcharge.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.databinding.TitleEditBinding


class TitleEditext @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private val binding: TitleEditBinding

    private var title: String = ""
    private var hint: String = ""

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.title_edit, this)
        binding = TitleEditBinding.bind(inflate)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TitleEditext,
            0, 0
        ).apply {
            try {
                title = getString(R.styleable.TitleEditext_title) ?: ""
                hint = getString(R.styleable.TitleEditext_hint) ?: ""
            } finally {
                recycle()
            }
        }


        initViews()

    }

    private fun initViews() {
        setTitle(title)
        setHint(hint)
    }


    fun setTitle(title: String?) {
        if (!title.isNullOrEmpty()) {
            binding.tvTitle.text = title
        }
    }

    fun setContent(content: String?) {
        if (!content.isNullOrEmpty()) {
            binding.etContent.setText(content)
        }
    }


    fun setHint(hint: String?) {
        if (!hint.isNullOrEmpty()) {
            binding.etContent.hint = hint
        }
    }

}


