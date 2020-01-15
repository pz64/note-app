package pzy64.xnotes.ui

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import org.jetbrains.anko.toast

object BindingAdapters {

    @BindingAdapter("boldFont")
    @JvmStatic
    fun EditText.boldText(index: Int)  {
        typeface = Fonts(this.context).FONTS[index].first
    }

    @BindingAdapter("regularFont")
    @JvmStatic
    fun EditText.regularText(index: Int)  {
        typeface = Fonts(this.context).FONTS[index].second
    }
}