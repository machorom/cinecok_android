package com.daou.cinecok.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.daou.cinecok.BR

open class BaseViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun getBinding() = binding
    fun bind(item: Any) {
        binding.setVariable(BR.binding_list_item, item)
        binding.executePendingBindings()
    }
}

