package pzy64.xnotes.ui.databinding

object BindingHelpers {

    @JvmStatic
    fun ellipsize(input: String, maxLength: Int): String {
        return if (input.length < maxLength) {
            input
        } else input.substring(0, maxLength)+ " ..."
    }
}