package com.daou.cinecok.ui.main.scrap

import androidx.recyclerview.widget.GridLayoutManager
import com.daou.cinecok.R
import com.daou.cinecok.base.BaseFragment
import com.daou.cinecok.databinding.FragmentScrapBinding
import com.daou.cinecok.ui.main.search.dialog.MovieDetailDlg
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScrapFragment : BaseFragment<FragmentScrapBinding>() {
    override val viewModel : ScrapViewModel by viewModel()
    override val layoutResourceId: Int
        get() =  R.layout.fragment_scrap

    override fun initDataBinding() {
        binding.vmScrap = viewModel
    }


    override fun initView() {
        with(binding) {
            rvFavoriteMovie.itemAnimator= null
            rvFavoriteMovie.layoutManager = GridLayoutManager((this@ScrapFragment).context,2)
            rvFavoriteMovie.adapter = ScrapListAdapter().apply{
                setOnItemClickListener(object : ScrapListAdapter.OnItemClickListener {
                    override fun onItemClick(itemIndex: Int) {
                        activity?.let { frgActivity ->
                            viewModel.favoriteMovieList.value?.get(itemIndex)?.let{ movieData->
                                MovieDetailDlg().let{
                                    it.setOnDissmissListener(object : MovieDetailDlg.OnDialogDismissListener{
                                        override fun onDismiss() {
                                            viewModel.loadFavoriteMovieList()
                                        }
                                    })
                                    it.show(frgActivity.supportFragmentManager,movieData )
                                }
                        }
                    }
                }})
            }


        }
    }


    override fun onResume() {
        viewModel.loadFavoriteMovieList()
        super.onResume()
    }
}