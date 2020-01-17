package pzy64.xnotes.ui.databinding

import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import pzy64.xnotes.ui.Colors
import pzy64.xnotes.ui.Fonts

object BindingAdapters {

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

    @BindingAdapter("boldFont")
    @JvmStatic
    fun TextView.boldText(index: Int)  {
        typeface = Fonts(this.context).FONTS[index].first
    }

    @BindingAdapter("regularFont")
    @JvmStatic
    fun TextView.regularText(index: Int)  {
        typeface = Fonts(this.context).FONTS[index].second
    }

    @BindingAdapter("backgroundColor")
    @JvmStatic
    fun ViewGroup.cardBackgroundColor(index: Int)  {
        setBackgroundColor(Colors.bg(index, 0xaa))
    }
}