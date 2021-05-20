package com.daou.cinecok.ui.main.home.screening

import android.view.ViewGroup
import com.daou.cinecok.R
import com.daou.cinecok.base.BaseAdapter
import com.daou.cinecok.base.BaseViewHolder

class ScreeningListAdapter : BaseAdapter() {
    override val layoutResourceId: Int
        get() = R.layout.item_recommend_movie

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return super.onCreateViewHolder(parent, viewType).apply {
            itemView.layoutParams.height = (parent.measuredWidth / 3 * 1.5).toInt()
        }
    }
}
