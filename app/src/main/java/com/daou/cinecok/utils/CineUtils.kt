package com.daou.cinecok.utils

import android.graphics.Rect
import android.view.View
import androidx.core.text.HtmlCompat
import java.util.*

class CineUtils {
    companion object {
        fun arrangeNApiResponseData(str : String) : String {
            lateinit var arrangedStr : String
            if (str.isEmpty())
                arrangedStr = "-"
            else if (str[str.lastIndex] == '|')
                arrangedStr = str.dropLast(1)
            arrangedStr = arrangedStr.replace("|", ", ")
            return arrangedStr
        }

        fun getCurrentDate() :List<Int> {
            var year: Int = 0
            var month: Int = 0
            var day: Int = 0
            var hour: Int = 0
            var min: Int = 0
            var sec: Int = 0
            val currentTime: Date = Calendar.getInstance().time

            java.text.SimpleDateFormat("yyyy", Locale.getDefault()).let {
                year = it.format(currentTime).toInt()
            }
            java.text.SimpleDateFormat("M", Locale.getDefault()).let {
                month = it.format(currentTime).toInt() - 1
            }
            java.text.SimpleDateFormat("d", Locale.getDefault()).let {
                day = it.format(currentTime).toInt()
            }
            java.text.SimpleDateFormat("H", Locale.getDefault()).let {
                hour = it.format(currentTime).toInt()
            }
            java.text.SimpleDateFormat("m", Locale.getDefault()).let {
                min = it.format(currentTime).toInt()
            }
            java.text.SimpleDateFormat("s", Locale.getDefault()).let {
                sec = it.format(currentTime).toInt()
            }

            return listOf(year, month, day, hour, min,sec)
        }


        fun removeHTMLTag(str : String) : String = HtmlCompat.fromHtml(str, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()


        fun setKeyboardShownListener(rootView: View, listener: KeyBoardShownListener){
            val SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128

            rootView.viewTreeObserver.addOnGlobalLayoutListener {
                val r = Rect()
                rootView.getWindowVisibleDisplayFrame(r)
                val dm = rootView.resources.displayMetrics
                val heightDiff = rootView.bottom - r.bottom
                val bShown = heightDiff > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density
                listener.onKeyboardShown(bShown)
            }
        }

    }


    interface KeyBoardShownListener{
        fun onKeyboardShown(bShown : Boolean)
    }
}