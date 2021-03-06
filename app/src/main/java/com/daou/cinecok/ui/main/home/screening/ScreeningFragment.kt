package com.daou.cinecok.ui.main.home.screening;

import androidx.recyclerview.widget.GridLayoutManager
import com.daou.cinecok.R
import com.daou.cinecok.base.BaseFragment
import com.daou.cinecok.databinding.FragmentRecommendBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScreeningFragment(private val url: String) : BaseFragment<FragmentRecommendBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_recommend
    override val viewModel: ScreeningViewModel by viewModel()

    override fun initDataBinding() {
        binding.vmRecommend = viewModel
    }

    override fun initView() {
        with(binding) {
            rvRecommendMovie.adapter = ScreeningListAdapter()
            rvRecommendMovie.itemAnimator = null
            rvRecommendMovie.layoutManager = GridLayoutManager((this@ScreeningFragment).context, 3)
        }

        viewModel.getRecommendMovieData(url)
    }
}