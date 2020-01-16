package pzy64.xnotes.ui

import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import org.jetbrains.anko.toast

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
        setBackgroundColor(Colors.COLORS[index])
    }
}