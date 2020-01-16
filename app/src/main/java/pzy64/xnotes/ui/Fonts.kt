package pzy64.xnotes.ui

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import pzy64.xnotes.R

class Fonts(val context: Context) {

    val FONTS = arrayOf(
        Pair(
            ResourcesCompat.getFont(context, R.font.robotomono_bold),
            ResourcesCompat.getFont(context, R.font.robotomono_regular)
        ),
        Pair(
            ResourcesCompat.getFont(context, R.font.opensans_bold),
            ResourcesCompat.getFont(context, R.font.opensans_regular)
        ),
        Pair(
            ResourcesCompat.getFont(context, R.font.rubik_bold),
            ResourcesCompat.getFont(context, R.font.rubik_regular)
        ),
        Pair(
            ResourcesCompat.getFont(context, R.font.quicksand_bold),
            ResourcesCompat.getFont(context, R.font.quicksand_regular)
        ),
        Pair(
            ResourcesCompat.getFont(context, R.font.dancingscript_bold),
            ResourcesCompat.getFont(context, R.font.dancingscript_regular)
        ),
        Pair(
            ResourcesCompat.getFont(context, R.font.robotoslab_bold),
            ResourcesCompat.getFont(context, R.font.robotoslab_regular)
        ),
        Pair(
            ResourcesCompat.getFont(context, R.font.cabin_sketch_bold),
            ResourcesCompat.getFont(context, R.font.cabin_sketch_regular)
        )
    )
}
