package com.daou.cinecok.ui.main.search.dialog

import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.FragmentManager
import com.daou.cinecok.R
import com.daou.cinecok.base.BaseFullScreenDialog
import com.daou.cinecok.databinding.DialogMovieGenreBinding
import com.daou.cinecok.ui.main.search.dialog.GenreSelectDlg.OnGenreSelectedListener.GenreCode
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenreSelectDlg : BaseFullScreenDialog<DialogMovieGenreBinding>(), View.OnClickListener {
    override val layoutResourceId: Int
        get() = R.layout.dialog_movie_genre
    override val viewModel: GenreSelectViewModel by viewModel()
    private lateinit var genreSelectedListener : OnGenreSelectedListener

    fun setGenreSelectedListener( listener : OnGenreSelectedListener) {
        genreSelectedListener = listener
    }


    fun show(manager: FragmentManager, genreCode: GenreCode) {
        super.show(manager,"")
        viewModel.insertGenreCode(genreCode)
    }


    private fun setCheck(genreCode : GenreCode, bCheck : Boolean) {
        with(binding) {
            when (genreCode) {
                GenreCode.ENTIRE -> rdMovieCategoryAll.isChecked = bCheck
                GenreCode.ACTION -> rdMovieCategoryAction.isChecked = bCheck
                GenreCode.ADVENTURE-> rdMovieCategoryAdventure.isChecked = bCheck
                GenreCode.ANI -> rdMovieCategoryAni.isChecked = bCheck
                GenreCode.CRIME ->rdMovieCategoryCrime.isChecked = bCheck
                GenreCode.DRAMA-> rdMovieCategoryDrama.isChecked = bCheck
                GenreCode.HORROR -> rdMovieCategoryHorror.isChecked = bCheck
                GenreCode.MARTIAL -> rdMovieCategoryMartial.isChecked = bCheck
                GenreCode.MUSICAL -> rdMovieCategoryMusical.isChecked = bCheck
                GenreCode.NOIR -> rdMovieCategoryNoir.isChecked = bCheck
                GenreCode.ROMANCE -> rdMovieCategoryRoman.isChecked = bCheck
                GenreCode.SF -> rdMovieCategorySf.isChecked = bCheck
                GenreCode.THRILLER -> rdMovieCategoryThriller.isChecked = bCheck
                GenreCode.WAR -> rdMovieCategoryWar.isChecked = bCheck
            }
        }
    }


    override fun onClick( genreSelection : View?) {
        genreSelection?.let{ view ->
            if( view is RadioButton){
                val rv = view as RadioButton
                val genreCode = rv.tag as GenreCode
                setCheck(viewModel.getGenreCode(),false)
                setCheck(genreCode,true)
                viewModel.insertGenreCode(genreCode)
                if(::genreSelectedListener.isInitialized)
                   genreSelectedListener.selectedGenre(genreCode)
            }
        }
    }


    override fun initView() {
        with(binding) {
            rdMovieCategoryAll.tag = GenreCode.ENTIRE
            rdMovieCategoryAction.tag = GenreCode.ACTION
            rdMovieCategoryAdventure.tag = GenreCode.ADVENTURE
            rdMovieCategoryAni.tag = GenreCode.ANI
            rdMovieCategoryDrama.tag = GenreCode.DRAMA
            rdMovieCategoryHorror.tag = GenreCode.HORROR
            rdMovieCategoryMartial.tag = GenreCode.MARTIAL
            rdMovieCategoryMusical.tag = GenreCode.MUSICAL
            rdMovieCategoryNoir.tag = GenreCode.NOIR
            rdMovieCategoryRoman.tag = GenreCode.ROMANCE
            rdMovieCategorySf.tag = GenreCode.SF
            rdMovieCategoryThriller.tag = GenreCode.THRILLER
            rdMovieCategoryWar.tag = GenreCode.WAR
            rdMovieCategoryCrime.tag = GenreCode.CRIME
            rdMovieCategoryAll.setOnClickListener(this@GenreSelectDlg)
            rdMovieCategoryAction.setOnClickListener(this@GenreSelectDlg)
            rdMovieCategoryAdventure.setOnClickListener(this@GenreSelectDlg)
            rdMovieCategoryAni.setOnClickListener(this@GenreSelectDlg)
            rdMovieCategoryDrama.setOnClickListener(this@GenreSelectDlg)
            rdMovieCategoryHorror.setOnClickListener(this@GenreSelectDlg)
            rdMovieCategoryMartial.setOnClickListener(this@GenreSelectDlg)
            rdMovieCategoryMusical.setOnClickListener(this@GenreSelectDlg)
            rdMovieCategoryNoir.setOnClickListener(this@GenreSelectDlg)
            rdMovieCategoryRoman.setOnClickListener(this@GenreSelectDlg)
            rdMovieCategorySf.setOnClickListener(this@GenreSelectDlg)
            rdMovieCategoryThriller.setOnClickListener(this@GenreSelectDlg)
            rdMovieCategoryWar.setOnClickListener(this@GenreSelectDlg)
            rdMovieCategoryCrime.setOnClickListener(this@GenreSelectDlg)

            btnBackCategory.setOnClickListener {
                this@GenreSelectDlg.dismiss()
            }
            setCheck(viewModel.getGenreCode(),true)
        }
    }



    interface OnGenreSelectedListener  {
        fun selectedGenre(genreCode : GenreCode)

        enum class GenreCode(private val n : Int){
            ENTIRE(0), DRAMA(1), HORROR(4), WAR(14), ADVENTURE(6), ANI(15)
            , THRILLER(7), ROMANCE(5), ACTION(19), MARTIAL(20), MUSICAL(17)
            , NOIR(8), CRIME(16), SF(18);

            companion object {
                private val VALUES = values()
                fun geyByValue(value : Int) = VALUES.firstOrNull{ it.n == value}
            }
            fun getNum() = n
        }
    }
}
