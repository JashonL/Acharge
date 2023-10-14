package com.dxm.dxmcharge.ui.settting.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.dxm.dxmcharge.databinding.DialogModeBinding
import com.tianji.ttech.base.BaseDialogFragment

class DialogMode : BaseDialogFragment(), View.OnClickListener {

    companion object {

        fun showDialog(
            fm: FragmentManager,
            onitemclick: OnItemclickLisener?=null
        ) {
            val dialog = DialogMode()
            dialog.onitemclick=onitemclick
            dialog.show(fm, DialogMode::class.java.name)
        }
    }

    private lateinit var binding: DialogModeBinding
    private var onitemclick: OnItemclickLisener? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogModeBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.llFast.setOnClickListener(this)
        binding.llPvlinkage.setOnClickListener(this)
        binding.llOffpeak.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when {
            v === binding.llFast -> {
                onitemclick?.onItemclick("fast")
                dismissAllowingStateLoss()
            }
            v === binding.llPvlinkage -> {
                onitemclick?.onItemclick("pvlink")
                dismissAllowingStateLoss()
            }
            v === binding.llOffpeak -> {
                onitemclick?.onItemclick("offpeak")
                dismissAllowingStateLoss()
            }

        }
    }


    interface OnItemclickLisener {
        fun onItemclick(mode: String)
    }


}