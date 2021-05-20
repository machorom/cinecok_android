package com.daou.cinecok.ui.main.search

import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.daou.cinecok.R
import com.daou.cinecok.base.BaseAdapter
import com.daou.cinecok.data.model.MovieData

object SearchBindAdapter {
    private const val PREVIEW_IMG_ROUNDING_RAD = 13

    @JvmStatic
    @BindingAdapter("onTouch")
    fun setOnTouchListener(view : EditText, onTouched:()->(Unit) ) {
        view.setOnTouchListener{ view: View, motionEvent: MotionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_UP) {
                onTouched()
            }
            false
        }
    }

    @JvmStatic
    @BindingAdapter("listData")
    fun setListData(recyclerView: RecyclerView, data: MutableList<Nothing>) {
        val adapter = recyclerView.adapter as ListAdapter<*,*>
        adapter.submitList(data.toMutableList())
    }

    @JvmStatic
    @BindingAdapter("bind_main_image")
    fun bindMainImage(view: ImageView, data : MovieData) {
        with(data) {
            Glide.with(view.context).run {
                if (imgURL.isNotBlank()) {
                    if (imgHighQualityURL.isBlank())
                        load(imgURL)
                    else
                        load(imgHighQualityURL)
                            .thumbnail(Glide.with(view.context).load(imgURL))
                }
                else {
                    load(
                        ResourcesCompat.getDrawable(
                            view.context.resources,
                            R.drawable.ic_noimg,
                            null
                        )
                    )
                }
            }.into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("bind_preview_image")
    fun bindPreviewImage(view: ImageView, url: String?) {
        Glide.with(view.context).run {
            if(!url.isNullOrBlank())
                load(url)
            else
                load(ResourcesCompat.getDrawable(view.context.resources,R.drawable.ic_noimg,null))
        }.transform(RoundedCorners(PREVIEW_IMG_ROUNDING_RAD)).into(view)
    }

    @JvmStatic
    @BindingAdapter("onScrollEndListener")
    fun bindOnScrollEndListener(view: RecyclerView, scrollEndListener: () -> (Unit)) {
        view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    scrollEndListener()
                }
            }
        })
    }

    @JvmStatic
    @BindingAdapter("bVisiblity")
    fun bindVisiblity(view: View, bVisible: Boolean) {
        if (bVisible)
            view.visibility = VISIBLE
        else
            view.visibility = INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("onReturnKeyClick")
    fun bindOnReturnKeyEvent(view: EditText, returnKeyListener: ()->(Unit)) {
        view.setOnKeyListener { view , keyCode , event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                returnKeyListener()
                true
            } else
                false
        }
    }
}