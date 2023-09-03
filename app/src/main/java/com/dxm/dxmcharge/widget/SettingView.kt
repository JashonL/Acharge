package com.dxm.dxmcharge.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.databinding.DataItemViewBinding
import com.dxm.dxmcharge.databinding.SettingViewBinding
import com.dxm.dxmcharge.extend.gone
import com.dxm.dxmcharge.extend.invisible
import com.dxm.dxmcharge.extend.visible

class SettingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr){



    private val binding: SettingViewBinding



    private var title: String?

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.setting_view, this)
        binding = SettingViewBinding.bind(view)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DataItemView,
            0, 0
        ).apply {
            try {
                title = getString(R.styleable.SettingView_title) ?: ""
            } finally {
                recycle()
            }
        }
        initView()
    }

    private fun initView() {
        if (!title.isNullOrEmpty()) {
            binding.tvTitle.text=title
        }

    }











}