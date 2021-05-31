package com.daou.cinecok.ui.main.search

import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.daou.cinecok.R
import com.daou.cinecok.base.BaseAdapter
import com.daou.cinecok.base.BaseFragment
import com.daou.cinecok.databinding.FragmentSearchBinding
import com.daou.cinecok.ui.main.search.dialog.GenreSelectDlg
import com.daou.cinecok.ui.main.search.dialog.GenreSelectDlg.OnGenreSelectedListener
import com.daou.cinecok.ui.main.search.dialog.GenreSelectDlg.OnGenreSelectedListener.*
import com.daou.cinecok.ui.main.search.dialog.MovieDetailDlg
import com.daou.cinecok.utils.CineUtils
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.angmarch.views.NiceSpinner
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModel()
    private val recordAdapter = RecordListAdapter()
    private val searchResultAdapter = object : BaseAdapter(){
        override val layoutResourceId: Int
            get() = R.layout.item_search_movie
    }
    private val filterDlg by lazy {
        GenreSelectDlg().apply {
            setGenreSelectedListener(object : OnGenreSelectedListener {
                override fun selectedGenre(genreCode: GenreCode) {
                    viewModel.setGenreCode(genreCode)
                }
            })
        }
    }


    override fun initDataBinding() {
        Log.d("TAG", "initDataBinding: viewModel ${viewModel}")
        binding.vmSearch = viewModel
    }


    override fun initView() {
        with(binding) {
            rvSearchRecord.itemAnimator = null
            rvSearchRecord.adapter = recordAdapter.apply {
                setOnItemClick(object : BaseAdapter.OnItemClickListener {
                    override fun onItemClick(itemIndex: Int, viewitemID: Int) {
                        when (viewitemID) {
                            R.id.btn_remove_record -> {
                                viewModel.removeRecord(itemIndex)
                            }

                            R.id.layout_search_record -> {
                                viewModel.searchFromRecord(itemIndex)
                            }
                        }
                    }

                })
            }
            rvSearchResult.adapter = searchResultAdapter.apply {
                setOnItemClick(object : BaseAdapter.OnItemClickListener {
                    override fun onItemClick(itemIndex: Int, viewitemID: Int) {
                        viewModel.getDetailData(itemIndex)
                    }
                })
            }

            btnSearchFilter.setOnClickListener {
                activity?.let {
                    filterDlg.show(it.supportFragmentManager, viewModel.getGenreCode())
                }
            }

            btnSearchMovie.setOnClickListener{
                viewModel.loadFirstPage()
            }

            spinnerMovieNation.setOnSpinnerItemSelectedListener { parent : NiceSpinner, view : View, position : Int, id : Long ->
                viewModel.setFilterNation(
                    (spinnerMovieNation.getItemAtPosition(position) as? String) ?: ""
                )
            }

        }


        with(viewModel) {
            showMovieDetail.observe(this@SearchFragment, Observer { movieData ->
                activity?.let {
                    MovieDetailDlg().show(it.supportFragmentManager,movieData )
                }
            })

            showNotExistSearchResult.observe(this@SearchFragment, Observer { bShown ->
                if(bShown)
                    binding.layoutNoResultSearch.visibility = VISIBLE
                else
                    binding.layoutNoResultSearch.visibility = INVISIBLE
            })
        }

        CineUtils.setKeyboardShownListener(binding.root, object : CineUtils.KeyBoardShownListener {
            override fun onKeyboardShown(bShown: Boolean) {
                if (bShown)
                    binding.layoutSearchRecord.visibility = VISIBLE
                else
                    binding.layoutSearchRecord.visibility = INVISIBLE
            }
        })
    }

}