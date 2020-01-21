package pzy64.xnotes.ui.baseclasses

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

import kotlin.coroutines.CoroutineContext

abstract class Pz64Fragment : Fragment(), CoroutineScope {
    private lateinit var job: Job

    var attached = false

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onAttach(context: Context) {
        super.onAttach(context)
        attached = true
    }

    override fun onDetach() {
        attached = false
        super.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun delayed(delay: Long = 300, block: () -> Unit) {
        Handler().postDelayed({
            if (attached) {
                block()
            }
        }, delay)
    }

}