package pzy64.xnotes.ui.baseclasses

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import pzy64.xnotes.Injetor
import pzy64.xnotes.ui.screens.Pz64ViewModel

import kotlin.coroutines.CoroutineContext

abstract class Pz64Fragment : Fragment(), CoroutineScope {
    private lateinit var job: Job

    var attached = false

    lateinit var viewModel: Pz64ViewModel
    private lateinit var factory:Pz64ViewModel.Factory

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onAttach(context: Context) {
        super.onAttach(context)
        attached = true
        factory = Injetor.provideVMFactory(context.applicationContext)
    }

    override fun onDetach() {
        attached = false
        super.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.apply {
            viewModel = ViewModelProvider(this, factory)[Pz64ViewModel::class.java]
        }
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