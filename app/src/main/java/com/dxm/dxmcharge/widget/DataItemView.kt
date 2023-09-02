package com.dxm.dxmcharge.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.databinding.DataItemViewBinding
import com.dxm.dxmcharge.extend.gone
import com.dxm.dxmcharge.extend.invisible
import com.dxm.dxmcharge.extend.visible

/**
 * 自定义组合View-设置页面Item
 */
class DataItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: DataItemViewBinding

    private var leftIcon: Drawable?
    private var showLeftIcon: Boolean = false
    private var showRightIcon: Boolean = true
    private var showSubName: Boolean = false
    private var showRedPoint: Boolean = false
    private var textSubNameColor: Int = resources.getColor(R.color.text_gray_66)
    private var itemName: String?
    private var subName: String?

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.data_item_view, this)
        binding = DataItemViewBinding.bind(view)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DataItemView,
            0, 0
        ).apply {
            try {
                leftIcon = getDrawable(R.styleable.DataItemView_leftIcon)
                showLeftIcon = getBoolean(R.styleable.DataItemView_showLeftIcon, false)
                showRightIcon = getBoolean(R.styleable.DataItemView_showRightIcon, true)
                showSubName = getBoolean(R.styleable.DataItemView_showSubName, false)
                showRedPoint = getBoolean(R.styleable.DataItemView_showRedPoint, false)
                textSubNameColor =
                    getColor(R.styleable.DataItemView_textSubNameColor, textSubNameColor)
                itemName = getString(R.styleable.DataItemView_itemName) ?: ""
                subName = getString(R.styleable.DataItemView_subName) ?: ""
            } finally {
                recycle()
            }
        }
        initView()
    }

    private fun initView() {
        if (showLeftIcon) {
            binding.ivLeft.setImageDrawable(leftIcon)
            binding.ivLeft.visible()
        } else {
            binding.ivLeft.gone()
        }
        if (showRightIcon) {
            binding.ivRight.visible()
        } else {
            binding.ivRight.invisible()
        }
        if (showSubName) {
            binding.tvItemSubName.visible()
        } else {
            binding.tvItemSubName.gone()
        }
        refreshRedPoint()
        binding.tvItemSubName.setTextColor(textSubNameColor)
        binding.tvItemName.text = itemName
        setSubName(subName)
    }

    fun setSubName(subName: String?) {
        if (showSubName) {
            this.subName = subName
            binding.tvItemSubName.text = subName ?: ""
        }
    }

    fun getSubName(): String {
        return binding.tvItemSubName.text.toString()
    }

    fun setRedPointText(redText: String) {
        binding.tvRedPoint.text = redText
    }

    fun showRedPoint(isShow: Boolean) {
        showRedPoint = isShow
        refreshRedPoint()
    }

    private fun refreshRedPoint() {
        if (showRedPoint) {
            binding.tvRedPoint.visible()
        } else {
            binding.tvRedPoint.gone()
        }
    }
}