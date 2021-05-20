package com.daou.cinecok.ui.main.home

import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.daou.cinecok.R
import com.daou.cinecok.base.BaseAdapter
import com.daou.cinecok.base.BaseFragment
import com.daou.cinecok.data.repository.MovieRepositoryImpl.Companion.GRADE_ORDER_MV_CRAWLING_URL
import com.daou.cinecok.data.repository.MovieRepositoryImpl.Companion.RESERVE_ORDER_MV_CRAWLING_URL
import com.daou.cinecok.databinding.FragmentHomeBinding
import com.daou.cinecok.ui.main.MainVPAdapter
import com.daou.cinecok.ui.main.home.screening.ScreeningFragment
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.concurrent.timer

class HomeFragment : BaseFragment<FragmentHomeBinding>(){
    override val layoutResourceId: Int
        get() =  R.layout.fragment_home
    override val viewModel : HomeViewModel by viewModel()
    private val vpAdapterScheduled = object : BaseAdapter(){
        override val layoutResourceId: Int
            get() = R.layout.item_scehduled_movie
    }
    private val vpAdapterRecommend by lazy {
        activity?.let { MainVPAdapter(it) }
    }


    override fun initDataBinding() {
        binding.vmHome = viewModel
    }


    override fun initView() {
        vpAdapterRecommend?.let {
            it.addFragment(ScreeningFragment(RESERVE_ORDER_MV_CRAWLING_URL)) // 예매순
            it.addFragment(ScreeningFragment(GRADE_ORDER_MV_CRAWLING_URL)) // 평점순
        }

        with(binding) {
            vpScheduledMovie.adapter = vpAdapterScheduled
            vpScheduledMovie.offscreenPageLimit = 1
            vpAdapterRecommend?.let{
                vpRecommendMovie.adapter = it
            }
            vpRecommendMovie.registerOnPageChangeCallback(object: OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tabCategoryHome.getTabAt(position)?.let {
                        tabCategoryHome.selectTab(it)
                    }
                    super.onPageSelected(position)
                }
            })
            tabCategoryHome.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {

                    tab?.let {
                        vpRecommendMovie.setCurrentItem(it.position, true)
                    }

                    if(tab != null)
                        vpRecommendMovie.setCurrentItem(tab.position,true)

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })


            timer(period = TIME_BANNER_SWIPE) {
                var viewPosition = vpScheduledMovie.currentItem
                viewPosition += 1
                if (viewPosition >= vpAdapterScheduled.itemCount)
                    viewPosition = 0
                (this@HomeFragment).activity?.runOnUiThread {
                    vpScheduledMovie.setCurrentItem(viewPosition, true)
                }
            }
        }

        with(viewModel) {
            scheduledMovieList.observe(this@HomeFragment, Observer{ list->
                vpAdapterScheduled.submitList(list.toList())
            })

            viewModel.getScheduledMovieList()
        }

    }

    companion object {
        var TIME_BANNER_SWIPE = 2000L // milli second
    }
}