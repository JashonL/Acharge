package com.dxm.dxmcharge.base.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dxm.dxmcharge.base.BaseViewHolder
import com.dxm.dxmcharge.databinding.HomeEmptyViewBinding

class EmptyViewHolder(itemView: View) :
    BaseViewHolder(itemView) {


    companion object {
        fun create(
            parent: ViewGroup,
            emptyTips:String?=""
        ): EmptyViewHolder {
            val binding = HomeEmptyViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            val holder = EmptyViewHolder(binding.root)
            holder.binding = binding
            binding.tvEmpty.text=emptyTips
            binding.root.setOnClickListener(holder)
            return holder
        }
    }

    private lateinit var binding: HomeEmptyViewBinding


}