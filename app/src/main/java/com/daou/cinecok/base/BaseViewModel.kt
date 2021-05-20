package com.daou.cinecok.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daou.cinecok.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel(){
    protected var _hideKeyboard = SingleLiveEvent<Void>()
    var hideKeyboard: LiveData<Void> = _hideKeyboard

    protected var _showToastMsg = SingleLiveEvent<String>()
    var showToastMsg: LiveData<String> = _showToastMsg

    protected var _showLoadingDlg = MutableLiveData<Boolean>()
    var showLoadingDlg: LiveData<Boolean> = _showLoadingDlg

}