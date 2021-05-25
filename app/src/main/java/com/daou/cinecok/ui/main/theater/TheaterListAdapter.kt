package com.daou.cinecok.ui.main.theater

import android.view.ViewGroup
import com.daou.cinecok.R
import com.daou.cinecok.base.BaseAdapter
import com.daou.cinecok.base.BaseViewHolder
import com.daou.cinecok.databinding.ItemTheaterBinding

class TheaterListAdapter : BaseAdapter() {
    override val layoutResourceId: Int
        get() = R.layout.item_theater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return super.onCreateViewHolder(parent, viewType).apply {

            (getBinding() as ItemTheaterBinding).root.setOnClickListener {
                onItemClickListener?.onItemClick(adapterPosition, getBinding().root.id)
            }
        }
    }
}