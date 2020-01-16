package pzy64.xnotes.ui

import android.graphics.Color


object Colors {
     fun getColorWithAlpha(color: Int, ratio: Float): Int {
        return Color.argb(Math.round(Color.alpha(color) * ratio), Color.red(color), Color.green(color), Color.blue(color))
    }
    val COLORS = arrayOf(
        0xFFFAFAFA.toInt(),
        0xFFFFCDD2.toInt(),
        0xFFF8BBD0.toInt(),
        0xFFE1BEE7.toInt(),
        0xFFB3E5FC.toInt(),
        0xFFB2DFDB.toInt(),
        0xFFC8E6C9.toInt(),
        0xFFDCEDC8.toInt(),
        0xFFF0F4C3.toInt(),
        0xFFFFF9C4.toInt(),
        0xFFFFECB3.toInt(),
        0xFFFFE0B2.toInt()
    )
}
