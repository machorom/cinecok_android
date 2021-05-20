package com.daou.cinecok.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/*
    BaseAdapter 상속받은 객체 필수 설정사항
    1. abstract val layoutResourceId: Int 리스트 뷰 아이템 리소스 아이디로 프로퍼티 할당 필요.
    2. binding 할 layout의 data식별자는 binding_list_item 으로
    3. diffutil의 비교는 데이터 객체의 toString을 재정의 해야함.
 */

abstract class BaseAdapter() : ListAdapter<Any, BaseViewHolder>(diff) {
    protected var onItemClickListener : BaseAdapter.OnItemClickListener? = null
    protected abstract val layoutResourceId: Int

    fun setOnItemClick(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int = layoutResourceId
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        val viewHolder = BaseViewHolder(binding)

        onItemClickListener?.let { listener ->
            binding.root.setOnClickListener {
                listener.onItemClick(viewHolder.adapterPosition, binding.root.id)
            }
        }

        return viewHolder
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.bind(getItem(position))

    companion object {
        val diff = object : DiffUtil.ItemCallback<Any>() {
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any) = newItem == oldItem
            override fun areItemsTheSame(oldItem: Any, newItem: Any) =
                oldItem.toString() == newItem.toString()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(itemIndex: Int, viewitemID : Int)
    }
}

