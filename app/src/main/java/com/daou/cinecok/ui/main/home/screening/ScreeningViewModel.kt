package com.daou.cinecok.ui.main.home.screening

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.daou.cinecok.base.BaseViewModel
import com.daou.cinecok.data.model.MovieData
import com.daou.cinecok.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ScreeningViewModel(private val repository : MovieRepository) : BaseViewModel(){
    private val _recommendMovieList = MutableLiveData<MutableList<MovieData>>()
    val recommendMovieList : LiveData<MutableList<MovieData>> = _recommendMovieList

    init {
        _recommendMovieList.value = mutableListOf()
    }
    fun getRecommendMovieData( url : String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecommendMovieList(url).let{
                _recommendMovieList.postValue(it.toMutableList())
            }
        }
    }
}