package com.daou.cinecok.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.daou.cinecok.ui.etc.LoadingDialogFragment

abstract class BaseFragment<T : ViewDataBinding>() : Fragment() {
    protected lateinit var binding: T
    protected abstract val layoutResourceId: Int
    protected abstract val viewModel : BaseViewModel
    private val loadingDialog = LoadingDialogFragment()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.lifecycleOwner = this
        initDataBinding()
        initView()


        with(viewModel) {
            hideKeyboard.observe(this@BaseFragment, Observer {
                this@BaseFragment.activity?.let { activity ->
                    val imm: InputMethodManager =
                        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    var view: View = activity.currentFocus ?: View(activity)
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
                }
            })

            showToastMsg.observe(this@BaseFragment, Observer { msg ->
                Toast.makeText(this@BaseFragment.context, msg, Toast.LENGTH_SHORT).show()
            })

            showLoadingDlg.observe(this@BaseFragment, Observer { bVisible ->
                if (bVisible) {
                    activity?.let {
                        if(!loadingDialog.isAdded)
                            loadingDialog.show(it.supportFragmentManager, "")
                    }
                } else
                    loadingDialog.dismiss()
            })


            return binding.root
        }

    }

    abstract fun initView()
    abstract fun initDataBinding()
}