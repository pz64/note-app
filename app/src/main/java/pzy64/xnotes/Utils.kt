package pzy64.xnotes

import android.os.Handler

fun delayed(delay: Long = 300, block: () -> Unit) {
    Handler().postDelayed({ block() }, delay)
}