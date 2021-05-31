package com.daou.cinecok.ui.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.daou.cinecok.R
import com.daou.cinecok.base.BaseViewModel
import com.daou.cinecok.data.localdb.entity.SearchRecordEntitiy
import com.daou.cinecok.data.model.MovieData
import com.daou.cinecok.data.model.NPageInfo
import com.daou.cinecok.data.repository.MovieRepository
import com.daou.cinecok.data.restapi.ResponseFlag.*
import com.daou.cinecok.data.restapi.NSearchMovieRequest
import com.daou.cinecok.ui.main.search.dialog.GenreSelectDlg.OnGenreSelectedListener.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: MovieRepository) : BaseViewModel() {
    private val _showMovieDetail = MutableLiveData<MovieData>()
    val showMovieDetail: LiveData<MovieData> = _showMovieDetail
    private val _showNotExistSearchResult = MutableLiveData<Boolean>()
    val showNotExistSearchResult: LiveData<Boolean> = _showNotExistSearchResult
    private val _searchedMovieList = MutableLiveData<MutableList<MovieData>>()
    val searchedMovieList : LiveData<MutableList<MovieData>> = _searchedMovieList
    private val _searchRecordList = MutableLiveData<MutableList<SearchRecordEntitiy>>()
    val searchRecordList : LiveData<MutableList<SearchRecordEntitiy>> = _searchRecordList
    val searchQuery : MutableLiveData<String> = MutableLiveData()
    private var genreCode = GenreCode.ENTIRE
    private var pageInfo = NPageInfo(0, 1, 0)
    private var selectedFilterNation : String = "전체"

    init {
        _searchRecordList.value = mutableListOf<SearchRecordEntitiy>()
        _searchedMovieList.value = mutableListOf<MovieData>()
    }

    fun setFilterNation( nation : String) {
        selectedFilterNation = nation
    }
    fun setGenreCode(genreCode : GenreCode) {
        this.genreCode = genreCode
    }


    fun getGenreCode() = genreCode

    fun searchFromRecord( listIndex : Int) {
        searchRecordList.run {
            value?.let {
                searchQuery.value = it[listIndex].searchTitle
                loadFirstPage()
            }
        }
    }
    fun removeRecord( listIndex : Int) {
        _searchRecordList.run {
            value?.let { listRecord ->
                val title = listRecord[listIndex].searchTitle
                viewModelScope.launch(Dispatchers.IO) {
                    repository.removeRecordData(title)
                }

                listRecord.removeAt(listIndex)
                postValue(value)
            }
        }
    }

    fun getDetailData(dataNum: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _showLoadingDlg.postValue(true)
            val searchData = searchedMovieList.value!!.get(dataNum)
            val resultDescriptionReq = repository.getMovieDescription(searchData.link) // {description, ResponseFlag}
            val highQualityImageURL = repository.getHighQualityImageURL(searchData.link)

            when (resultDescriptionReq.responseFlag) {
                SUCCESS -> {
                    searchedMovieList.value?.get(dataNum)?.let { movieData ->
                        movieData.movieDescription = resultDescriptionReq.responseData
                        if( movieData.imgURL.isNotEmpty() && highQualityImageURL.isNotEmpty() )
                            movieData.imgHighQualityURL = highQualityImageURL
                        _showMovieDetail.postValue(movieData)
                    }
                }
                CONNECT_FAIL -> {
                    repository.getSysResources().getString(R.string.alert_network_error)
                        .let { msg ->
                            _showToastMsg.postValue(msg)
                        }
                }
            }

            _showLoadingDlg.postValue(false)
        }
    }

    fun onRecordItemClick(n: Int, viewID: Int) {


    }


    fun loadSearchRecord() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getRecordList()
            _searchRecordList.postValue(result.toMutableList())
        }
    }


    fun loadNextPage() {
        if (!pageInfo.isNextPageExist())
            return

        viewModelScope.launch(Dispatchers.IO) {
            _showLoadingDlg.postValue(true)
            val result = repository.getMovieList(makeMovieInfoRequestQuery(false))
            when (result.repoResult.responseFlag) {
                SUCCESS -> {
                    pageInfo = result.pageInfo
                    _searchedMovieList.apply {
                        value?.addAll(result.repoResult.responseData)
                        postValue(this.value)
                    }
                }

                CONNECT_FAIL -> {
                    repository.getSysResources().getString(R.string.alert_network_error)
                        .let { msg ->
                            _showToastMsg.postValue(msg)
                        }
                }
            }

            _showLoadingDlg.postValue(false)
        }
    }


    fun loadFirstPage() {
        if (searchQuery.value.isNullOrBlank()) {
            repository.getSysResources().getString(R.string.alert_no_input_search).let { msg ->
                _showToastMsg.postValue(msg)
            }
            return
        }

        _hideKeyboard.call()
        viewModelScope.launch(Dispatchers.IO) {
            _showLoadingDlg.postValue(true)
            repository.saveRecordData(searchQuery.value!!)
            val result = repository.getMovieList(makeMovieInfoRequestQuery(true))
            when (result.repoResult.responseFlag) {
                SUCCESS -> {
                    pageInfo.searchStartIdx = result.pageInfo.searchStartIdx
                    pageInfo.searchNextStartIdx = result.pageInfo.searchNextStartIdx
                    pageInfo.searchMaxIdx = result.pageInfo.searchMaxIdx
                    _searchedMovieList.postValue(result.repoResult.responseData.toMutableList())
                    _showNotExistSearchResult.postValue(false)
                }

                NO_RESULT -> _showNotExistSearchResult.postValue(true)

                CONNECT_FAIL -> {
                    repository.getSysResources().getString(R.string.alert_network_error)
                        .let { msg ->
                            _showToastMsg.postValue(msg)
                        }
                }
            }

            _showLoadingDlg.postValue(false)
        }
    }

    private fun makeMovieInfoRequestQuery(isFirstRequest: Boolean): NSearchMovieRequest {
        val query = searchQuery.value ?: ""
        val genreNum = genreCode.getNum()
        val start = if (isFirstRequest) 1 else pageInfo.searchNextStartIdx
        val nationCode = getNationCode(selectedFilterNation)
        return NSearchMovieRequest(query, genreNum, start, nationCode)
    }

    private fun getNationCode( nation : String ) : String {
        lateinit var nationCode : String
        when (nation) {
            "한국" -> nationCode = "KR"
            "일본" -> nationCode = "JP"
            "미국" -> nationCode = "US"
            "홍콩" -> nationCode = "HK"
            "영국" -> nationCode = "GB"
            "프랑스" -> nationCode = "FR"
            else -> nationCode = "" // 전체
        }

        return nationCode
    }

}