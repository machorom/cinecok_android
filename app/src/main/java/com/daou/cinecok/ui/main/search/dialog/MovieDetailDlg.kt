package com.daou.cinecok.ui.main.search.dialog

import android.content.DialogInterface
import android.view.*
import android.widget.CompoundButton
import androidx.fragment.app.FragmentManager
import com.daou.cinecok.R
import com.daou.cinecok.base.BaseFullScreenDialog
import com.daou.cinecok.data.model.MovieData
import com.daou.cinecok.databinding.DialogMovieDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailDlg() : BaseFullScreenDialog<DialogMovieDetailBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_movie_detail
    override val viewModel: MovieDetailViewModel by viewModel()
    lateinit private var onDismissListener : OnDialogDismissListener

    fun show(manager: FragmentManager,  movieData : MovieData) {
        super.show(manager, "")
        viewModel.insertMovieData(movieData)
    }

    override fun initView() {
        with(binding) {
            vmMovieDetail = viewModel
            movieInfo = viewModel.getMovieData()
            btnBackMovieDetail.setOnClickListener {
                this@MovieDetailDlg.dismiss()
            }

            with(viewModel) {
                toggleFavoriteMovie.setOnCheckedChangeListener{ compoundButton: CompoundButton, bToggled: Boolean ->
                    if(bToggled) {
                        saveFavoriteMovie(getMovieData())
                    } else {
                        removeFavoriteMovie(getMovieData().compareKey)
                    }
                }

                getFavoriteOrNot(getMovieData().compareKey)
            }

        }
    }

    fun setOnDissmissListener(listener : OnDialogDismissListener) {
        onDismissListener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        if(::onDismissListener.isInitialized)
            onDismissListener.onDismiss()
        super.onDismiss(dialog)
    }


    interface OnDialogDismissListener {
        fun onDismiss()
    }

}
