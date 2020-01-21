package pzy64.xnotes.ui.screens.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.launch
import pzy64.xnotes.Injetor
import pzy64.xnotes.R
import pzy64.xnotes.data.model.Note
import pzy64.xnotes.hide
import pzy64.xnotes.show
import pzy64.xnotes.ui.LastItemSpace
import pzy64.xnotes.ui.baseclasses.Pz64Fragment
import pzy64.xnotes.ui.screens.Pz64ViewModel


class MainFragment : Pz64Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: Pz64ViewModel

    private lateinit var factory: Pz64ViewModel.Factory

    private val placeHolderLoading = "LOADING.."
    private val placeholderAddNote = "ADD\nNOTE"

    private val noteClicked: (Note) -> Unit = { note ->
        viewModel.currentNote.value = note
        viewModel.editMode.value = true
        findNavController().navigate(R.id.destinationCreateNote)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        factory = Injetor.provideVMFactory(context.applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.apply {
            viewModel = ViewModelProviders.of(this, factory).get(Pz64ViewModel::class.java)

            setupUi()
        }
    }

    private fun setupUi() {
        placeHolder.text = placeHolderLoading
        placeHolder.show()

        placeHolder.setOnClickListener {
            if (placeHolder.text == placeholderAddNote) {
                findNavController().navigate(R.id.destinationCreateNote)
            }
        }

        val layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        notesRecyclerView.layoutManager = layoutManager
        val space = resources.getDimensionPixelSize(R.dimen.actionBarSize)
        notesRecyclerView.addItemDecoration(LastItemSpace(space, false))

        launch {
            viewModel.getNotes().observe(this@MainFragment, Observer {

                it?.let { notes ->
                    if (notes.isNotEmpty()) {
                        val mutableNotes = notes.toMutableList()



                        notesRecyclerView.adapter = NotesAdapter(mutableNotes, noteClicked)
                        placeHolder.hide()
                    } else {
                        placeHolder.text = placeholderAddNote
                        placeHolder.show()
                    }
                }
            })
        }
    }
}
