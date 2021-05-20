package com.daou.cinecok.ui.main.search.dialog

import androidx.lifecycle.ViewModel
import com.daou.cinecok.ui.main.search.dialog.GenreSelectDlg.OnGenreSelectedListener.GenreCode


class GenreSelectViewModel : ViewModel() {
    private lateinit var genreCode: GenreCode
    
    fun insertGenreCode( genreCode : GenreCode) {
        this.genreCode=  genreCode
    }
    fun getGenreCode() = genreCode
}