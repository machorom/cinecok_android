package com.daou.cinecok.ui.main.search

import android.view.ViewGroup
import com.daou.cinecok.R
import com.daou.cinecok.base.BaseAdapter
import com.daou.cinecok.base.BaseViewHolder
import com.daou.cinecok.databinding.ItemSearchRecordBinding

class RecordListAdapter : BaseAdapter() {
    override val layoutResourceId: Int
        get() = R.layout.item_search_record


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return super.onCreateViewHolder(parent, viewType).apply {
            (getBinding() as ItemSearchRecordBinding).btnRemoveRecord.setOnClickListener {
                onItemClickListener?.onItemClick(adapterPosition, R.id.btn_remove_record)
            }
        }
    }

}