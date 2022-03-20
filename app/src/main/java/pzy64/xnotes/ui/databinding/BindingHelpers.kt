package pzy64.xnotes.ui.databinding

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*


object BindingHelpers {

    @JvmStatic
    fun ellipsize(input: String, maxLength: Int): String {
        return if (input.length < maxLength) {
            input
        } else input.substring(0, maxLength) + " ..."
    }

    @JvmStatic
    fun getRelativeDate(date: Long) =
         DateUtils.getRelativeTimeSpanString(
            date,
            System.currentTimeMillis(),
            DateUtils.SECOND_IN_MILLIS
        ).toString()

    @JvmStatic
    fun getDate(date: Long): String {
        try {
            val calendar: Calendar = Calendar.getInstance()
            calendar.setTimeInMillis(date)
            val sdf = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", Locale.getDefault())
            val dt: Date = calendar.getTime() as Date
            return DateUtils.getRelativeTimeSpanString(date, System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS).toString()
        } catch (e: Exception) {
        }
        return ""
    }
}