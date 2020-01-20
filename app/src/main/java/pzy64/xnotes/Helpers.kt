package pzy64.xnotes

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Handler
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import org.jetbrains.anko.toast

fun delayed(delay: Long = 300, block: () -> Unit) {
    Handler().postDelayed({ block() }, delay)
}

fun Fragment.toast(msg: String) = context?.toast(msg)


fun View.scaleInAnim(delay: Long = 500): AnimatorSet {

    val scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1.04f, 1.0f)
    scaleX.duration = delay
    scaleX.interpolator = DecelerateInterpolator(1f)

    val scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1.04f, 1.0f)
    scaleY.duration = delay
    scaleY.interpolator = DecelerateInterpolator(1f)

    val alpha = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
    alpha.duration = delay
    alpha.interpolator = DecelerateInterpolator(1f)

    val set = AnimatorSet()
    set.playTogether(scaleX, scaleY, alpha)
    set.start()

    return set
}

fun View.scaleOutAnim(delay: Long = 500): AnimatorSet {

    val scaleX = ObjectAnimator.ofFloat(this, "scaleX", 0.96f, 1.0f)
    scaleX.duration = delay
    scaleX.interpolator = DecelerateInterpolator(1f)

    val scaleY = ObjectAnimator.ofFloat(this, "scaleY", 0.96f, 1.0f)
    scaleY.duration = delay
    scaleY.interpolator = DecelerateInterpolator(1f)

    val alpha = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
    alpha.duration = delay
    alpha.interpolator = DecelerateInterpolator(1f)

    val set = AnimatorSet()
    set.playTogether(scaleX, scaleY, alpha)
    set.start()

    return set
}