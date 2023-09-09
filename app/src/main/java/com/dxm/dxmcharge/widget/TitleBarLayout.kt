package com.dxm.dxmcharge.widget

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.databinding.TitleBarLayoutBinding
import com.dxm.dxmcharge.extend.gone
import com.dxm.dxmcharge.extend.setDrawableEnd
import com.dxm.dxmcharge.extend.setDrawableNull
import com.dxm.dxmcharge.extend.visible

/**
 * 自定义组合View-标题栏
 */
class TitleBarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private val binding: TitleBarLayoutBinding

    private var showLeftBackIcon: Boolean = true
    private var showRightButton: Boolean = false
    private var showRightText: Boolean = false
    private var showRightImage: Boolean = false
    private var showFilterIcon: Boolean = false
    private var showRightMenu2: Boolean = false
    private var showleftLogo: Boolean = false

    private var titleText: String? = null
    private var rightText: String? = null
    private var rightButtonText: String? = null
    private var rigthMenuDrawable: Drawable? = null
    private var rigthMenu2Drawable: Drawable? = null
    private var leftDrawable: Drawable? = null


    private var leftIconClickListener: ((View?) -> Unit)? = null

    private var leftIvClickListener: ((View?) -> Unit)? = null

    private var rightButtonClickListener: ((View?) -> Unit)? = null
    private var rightTextClickListener: ((View?) -> Unit)? = null
    private var rightImageClickListener: ((View?) -> Unit)? = null
    private var titleClickListener: ((View?) -> Unit)? = null
    private var rightMenu2IvClick: ((View?) -> Unit)? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.title_bar_layout, this)
        binding = TitleBarLayoutBinding.bind(view)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TitleBarLayout,
            0, 0
        ).apply {
            try {
                showLeftBackIcon =
                    getBoolean(R.styleable.TitleBarLayout_showLeftBackIcon, showLeftBackIcon)
                showRightButton =
                    getBoolean(R.styleable.TitleBarLayout_showRightButton, showRightButton)
                showRightText =
                    getBoolean(R.styleable.TitleBarLayout_showRightText, showRightText)
                showRightImage =
                    getBoolean(R.styleable.TitleBarLayout_showRightImage, showRightImage)
                showFilterIcon =
                    getBoolean(R.styleable.TitleBarLayout_showFilterIcon, showFilterIcon)

                showRightMenu2 =
                    getBoolean(R.styleable.TitleBarLayout_showRightMenu2, showRightMenu2)

                showleftLogo=getBoolean(R.styleable.TitleBarLayout_left_showlogo,showleftLogo)


                titleText = getString(R.styleable.TitleBarLayout_titleText) ?: ""
                rightText = getString(R.styleable.TitleBarLayout_rightText) ?: ""
                rightButtonText = getString(R.styleable.TitleBarLayout_rightButtonText) ?: ""

                rigthMenu2Drawable = getDrawable(R.styleable.TitleBarLayout_rightmenu2Icon)
                rigthMenuDrawable=getDrawable(R.styleable.TitleBarLayout_right_icon)
                leftDrawable=getDrawable(R.styleable.TitleBarLayout_barLeftIcon)


            } finally {
                recycle()
            }
        }
        initView()
    }

    private fun initView() {
        binding.tvTitle.text = titleText
        if (showRightButton) {
            binding.btRight.visible()
            binding.btRight.text = rightButtonText
        } else {
            binding.btRight.gone()
        }
        if (showLeftBackIcon) {
            binding.ivBack.visible()
        } else {
            binding.ivBack.gone()
        }
        if (showRightText) {
            binding.tvRightText.visible()
            binding.tvRightText.text = rightText
        } else {
            binding.tvRightText.gone()
        }





        showIvLeft()
        showRightImage()
        showFilterIconView()
        showMenu2()

        binding.ivBack.setOnClickListener(this)
        binding.btRight.setOnClickListener(this)
        binding.tvRightText.setOnClickListener(this)
        binding.ivRight.setOnClickListener(this)
        binding.tvTitle.setOnClickListener(this)
        binding.ivMenu2.setOnClickListener(this)
        binding.ivLeft.setOnClickListener(this)
    }



    private fun showIvLeft() {

        if (showleftLogo){
            binding.ivLeft.visible()
        }else{
            binding.ivLeft.gone()
        }

        if (leftDrawable != null) {
            binding.ivLeft.setImageDrawable(leftDrawable)
        }

        if (rigthMenuDrawable!=null){
            binding.ivLeft.setImageDrawable(leftDrawable)
        }

    }



    private fun showMenu2() {
        if (showRightMenu2) {
            binding.ivMenu2.visible()
        } else {
            binding.ivMenu2.gone()
        }
        if (rigthMenu2Drawable != null) {
            binding.ivMenu2.setImageDrawable(rigthMenu2Drawable)
        }

        if (rigthMenuDrawable!=null){
            binding.ivRight.setImageDrawable(rigthMenuDrawable)
        }

    }

    private fun showRightImage() {
        if (showRightImage) {
            binding.ivRight.visible()
        } else {
            binding.ivRight.gone()
        }
    }

    private fun showFilterIconView() {
        if (showFilterIcon) {
            binding.tvTitle.setDrawableEnd(resources.getDrawable(R.drawable.ic_down))
        } else {
            binding.tvTitle.setDrawableNull()
        }
    }

    fun setFilterIconVisible(isVisible: Boolean) {
        showFilterIcon = isVisible
        showFilterIconView()
    }

    fun setRightImageVisible(isVisible: Boolean) {
        showRightImage = isVisible
        showRightImage()
    }

    override fun onClick(v: View?) {
        when {
            v===binding.ivLeft->{
                leftIvClickListener?.invoke(v)
            }

            v === binding.ivBack -> {
                if (leftIconClickListener == null) {
                    (context as? Activity)?.onBackPressed()
                } else {
                    leftIconClickListener?.invoke(v)
                }
            }
            v === binding.btRight -> {
                rightButtonClickListener?.invoke(v)
            }
            v === binding.tvRightText -> {
                rightTextClickListener?.invoke(v)
            }
            v === binding.ivRight -> {
                rightImageClickListener?.invoke(v)
            }
            v === binding.tvTitle -> {
                titleClickListener?.invoke(v)
            }
            v === binding.ivMenu2 -> {
                rightMenu2IvClick?.invoke(v)
            }
        }
    }


    fun setOnLeftIvClickListener(leftIconClickListener: (v: View?) -> Unit) {
        this.leftIvClickListener = leftIconClickListener
    }


    fun setOnLeftIconClickListener(leftIconClickListener: (v: View?) -> Unit) {
        this.leftIconClickListener = leftIconClickListener
    }

    fun setOnRightButtonClickListener(rightButtonClickListener: (v: View?) -> Unit) {
        this.rightButtonClickListener = rightButtonClickListener
    }

    fun setOnRightTextClickListener(rightTextClickListener: (v: View?) -> Unit) {
        this.rightTextClickListener = rightTextClickListener
    }


    fun setOnMenu2ClickListener(rightMenu2IvClick: (v: View?) -> Unit) {
        this.rightMenu2IvClick = rightMenu2IvClick
    }

    fun setOnRightImageClickListener(rightImageClickListener: (v: View?) -> Unit) {
        this.rightImageClickListener = rightImageClickListener
    }

    fun setOnTitleClickListener(titleClickListener: (v: View?) -> Unit) {
        this.titleClickListener = titleClickListener
    }

    fun setRightText(rightText: String?) {
        if (showRightText) {
            this.rightText = rightText
            binding.tvRightText.text = rightText ?: ""
        }
    }

    fun setTitleText(titleText: String?) {
        this.titleText = titleText
        binding.tvTitle.text = titleText ?: ""
    }


    fun getTitilView(): TextView {
        return binding.tvTitle
    }

    fun getRightView(): View {
        return binding.rlMenu1
    }


}