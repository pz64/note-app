package pzy64.xnotes.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*
import org.jetbrains.anko.toast
import pzy64.xnotes.Injetor
import pzy64.xnotes.R
import pzy64.xnotes.ui.baseclasses.Pz64Fragment

class MainFragment : Pz64Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var factory: MainViewModel.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        factory = Injetor.provideMainVMFactory(context.applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        setupUi()
    }

    private fun setupUi() {

        val layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        notesRecyclerView.layoutManager = layoutManager

        viewModel.getNotes().observe(this, Observer {
            it?.let { notes ->

                if (notes.isNotEmpty()) {
                    notesRecyclerView.adapter = NotesAdapter(notes)
                    emptyNotePlaceHolder.visibility = View.GONE
                }
                else {
                    emptyNotePlaceHolder.visibility = View.VISIBLE
                }
            }
        })
    }

}
