package pzy64.xnotes.ui

import androidx.core.graphics.ColorUtils


object Colors {

    val size get() = COLORS.size

    fun bg(index: Int, alpha: Int) = ColorUtils.setAlphaComponent(COLORS[index], alpha)

    fun blendInWhite(index: Int, alpha: Int) =
        ColorUtils.blendARGB(bg(index, alpha), 0xFFFFFF, 0.5f)

    fun isDark(index: Int,  alpha: Int): Boolean {
        return ColorUtils.calculateLuminance(bg(index, alpha)) < 0.5
    }

    private val COLORS = arrayOf(
        0xFFDADADA.toInt(),
        0xFF9E9E9E.toInt(),
        0xFFF44336.toInt(),
        0xFF673AB7.toInt(),
        0xFF03A9F4.toInt(),
        0xFF4CAF50.toInt(),
        0xFF009688.toInt(),
        0xFFFFEB3B.toInt(),

        0xFFF50057.toInt(),
        0xFF9C27B0.toInt(),
        0xFF2196F3.toInt(),
        0xFF009688.toInt(),
        0xFFEEFF41.toInt(),
        0xFF76FF03.toInt(),
        0xFF1DE9B6.toInt()
    )
}
