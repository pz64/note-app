package pzy64.xnotes.ui

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import org.jetbrains.anko.toast

object BinderAdsapters {

    @BindingAdapter("boldFont")
    @JvmStatic
    fun TextInputEditText.boldText(index: Int)  {
        typeface = Fonts(this.context).FONTS[index].first
    }

    @BindingAdapter("regularFont")
    @JvmStatic
    fun TextInputEditText.regularText(index: Int)  {
        typeface = Fonts(this.context).FONTS[index].second
    }
}