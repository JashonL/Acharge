package com.tianji.ttech.base.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dxm.dxmcharge.databinding.ItemLoadingEndViewHolderBinding
import com.dxm.dxmcharge.base.BaseViewHolder

/**
 * 全部加载完成
 */
class ItemLoadingEndViewHolder(itemView: View) : BaseViewHolder(itemView) {

    companion object {
        fun create(
            parent: ViewGroup,
        ): ItemLoadingEndViewHolder {
            val binding = ItemLoadingEndViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            val holder = ItemLoadingEndViewHolder(binding.root)
            holder.binding = binding
            return holder
        }
    }

    private lateinit var binding: ItemLoadingEndViewHolderBinding
}