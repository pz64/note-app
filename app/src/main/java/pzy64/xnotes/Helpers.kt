package pzy64.xnotes

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import org.jetbrains.anko.configuration
import org.jetbrains.anko.toast


fun Fragment.toast(msg: String) = context?.toast(msg)


fun View.scaleOutAnim(delay: Long = 500): AnimatorSet {

    val scalex = ObjectAnimator.ofFloat(this, "scaleX", 0.99f, 1f)
    scalex.duration = delay
    scalex.interpolator = DecelerateInterpolator(1f)

    val scaley = ObjectAnimator.ofFloat(this, "scaleY", 0.99f, 1f)
    scaley.duration = delay
    scaley.interpolator = DecelerateInterpolator(1f)

    val alpha = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
    alpha.duration = delay
    alpha.interpolator = DecelerateInterpolator(1f)

    val set = AnimatorSet()
    set.playTogether(alpha, scalex, scaley)
    set.start()

    return set
}

fun View.show(delay: Long = 350) {
    val alpha = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
    alpha.duration = delay
    alpha.interpolator = DecelerateInterpolator(1f)
    alpha.start()
    alpha.addListener(object : Animator.AnimatorListener {

        override fun onAnimationRepeat(animation: Animator?) {}
        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationStart(animation: Animator?) {}
        override fun onAnimationEnd(animation: Animator?) {
            visibility = View.VISIBLE
        }
    })
}

fun View.hide(delay: Long = 500) {
    val alpha = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)
    alpha.duration = delay
    alpha.interpolator = DecelerateInterpolator(1f)
    alpha.start()
    alpha.addListener(object : Animator.AnimatorListener {

        override fun onAnimationRepeat(animation: Animator?) {}
        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationStart(animation: Animator?) {}
        override fun onAnimationEnd(animation: Animator?) {
            visibility = View.GONE
        }
    })
}

fun Context.hideKeyboard(view: View) {
    val imm: InputMethodManager =
        this.getSystemService(android.app.Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View) {
    val imm: InputMethodManager =
        this.getSystemService(android.app.Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.onDarkMode(block: () -> Unit) {
    val currentNightMode = configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
        block()
    }
}

fun Context.onLightMode(block: () -> Unit) {
    val currentNightMode = configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
        block()
    }
}