package com.daou.cinecok.ui.main.scrap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.daou.cinecok.base.BaseViewModel
import com.daou.cinecok.data.model.MovieData
import com.daou.cinecok.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScrapViewModel(private val repository : MovieRepository): BaseViewModel(){
    private val _favoriteMovieList = MutableLiveData<MutableList<MovieData>>()
    val favoriteMovieList : LiveData<MutableList<MovieData>> = _favoriteMovieList
    init {
        _favoriteMovieList.value = mutableListOf()
    }

    fun loadFavoriteMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavoriteMovie().let{
                _favoriteMovieList.postValue(it.toMutableList())
            }
        }
    }
}