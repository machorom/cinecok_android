package com.daou.cinecok.ui.main.theater

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.daou.cinecok.R
import com.daou.cinecok.base.BaseViewModel
import com.daou.cinecok.data.model.KPageInfo
import com.daou.cinecok.data.repository.TheaterRepository
import com.daou.cinecok.data.restapi.KTheaterData
import com.daou.cinecok.data.restapi.ResponseFlag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TheaterViewModel(private val repository: TheaterRepository): BaseViewModel() {

    private val TAG = "TheaterViewModel"

    companion object {
        val DEFAULT_PAGE_NUM = 1
        val VIEW_TYPE_LIST = 0
        val VIEW_TYPE_MAP = 1
    }

    private val _showNotExistResult = MutableLiveData<Boolean>().apply {
        value = true
    }
    val showNotExistResult : LiveData<Boolean> = _showNotExistResult

    private val _theaterList = MutableLiveData<MutableList<KTheaterData>>().apply {
        value = mutableListOf()
    }
    val theaterList : LiveData<MutableList<KTheaterData>> = _theaterList

    var location: Location? = null

    private var pageInfo = KPageInfo(DEFAULT_PAGE_NUM, true)

    private val _crrViewType = MutableLiveData<Int>().apply {
        value = VIEW_TYPE_LIST
    }
    val crrViewType: LiveData<Int> = _crrViewType

    private val _flagRefresh = MutableLiveData<Boolean>().apply { false }
    val flagRefresh: LiveData<Boolean> = _flagRefresh

    fun loadPage() {
        Log.d(TAG, "loadPage() called , page : ${pageInfo.lastPageNum}")

        if (!pageInfo.hasNext) return

        location?.let {
            viewModelScope.launch(Dispatchers.IO) {
                _showLoadingDlg.postValue(true)
                val result = repository.getTheaterList(it.longitude, it.latitude, pageInfo.lastPageNum)
                when (result.repoResult.responseFlag) {
                    ResponseFlag.SUCCESS -> {
                        //KAKAKO API 는 CurrentPage 제공 X
                        pageInfo.lastPageNum++
                        pageInfo.hasNext = result.pageInfo.hasNext
                        _theaterList.apply {
                            Log.d(TAG, "loadPage() called $value")
                            value?.addAll(result.repoResult.responseData.toMutableList())
                            postValue(value)
                        }
                    }

                    ResponseFlag.NO_RESULT -> _showNotExistResult.postValue(true)

                    ResponseFlag.CONNECT_FAIL -> {
                        repository.getSysResources().getString(R.string.alert_network_error)
                            .let { msg ->
                                _showToastMsg.postValue(msg)
                            }
                    }
                }
                _showLoadingDlg.postValue(false)
            }
        }
    }

    fun onChangeMode() {
        when (crrViewType.value) {
            VIEW_TYPE_LIST -> _crrViewType.postValue(VIEW_TYPE_MAP)
            VIEW_TYPE_MAP -> _crrViewType.postValue(VIEW_TYPE_LIST)
        }
    }

    fun onUpdateLoaction(isRefresh: Boolean) {
        if (isRefresh) {
            _showToastMsg.postValue("갱신 되었습니다")
            Log.d(TAG, "onUpdateLoaction: ${location}")
        }
        _flagRefresh.postValue(isRefresh)
    }
}