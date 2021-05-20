package com.daou.cinecok.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.daou.cinecok.base.BaseViewModel
import com.daou.cinecok.data.model.MovieData
import com.daou.cinecok.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository : MovieRepository) : BaseViewModel(){
    private val _scheduledMovieList = MutableLiveData<MutableList<MovieData>>()
    val scheduledMovieList: LiveData<MutableList<MovieData>> = _scheduledMovieList

    fun getScheduledMovieList() {
        viewModelScope.launch(Dispatchers.IO){
            val list = repository.getScheduledMovieList()
            _scheduledMovieList.postValue(list.toMutableList())
        }
    }
}