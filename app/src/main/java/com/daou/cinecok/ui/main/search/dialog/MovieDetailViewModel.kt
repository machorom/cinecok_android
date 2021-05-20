package com.daou.cinecok.ui.main.search.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daou.cinecok.data.model.MovieData
import com.daou.cinecok.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repository: MovieRepository)  : ViewModel() {
    private val _isFavoriteMovie = MutableLiveData<Boolean>()
    val isFavoriteMovie : LiveData<Boolean> = _isFavoriteMovie
    private lateinit var movieData : MovieData

    fun saveFavoriteMovie(movieData : MovieData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveFavoriteMovieData(movieData)
        }
    }
    fun removeFavoriteMovie(compareKey : String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFavoriteMovieData(compareKey)
        }
    }

    fun getFavoriteOrNot(compareKey : String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isFavoriteMovie.postValue(repository.getExistFavoriteMovie(compareKey))
        }
    }

    fun getMovieData() = movieData
    fun insertMovieData(data : MovieData){
        movieData = data
    }
}