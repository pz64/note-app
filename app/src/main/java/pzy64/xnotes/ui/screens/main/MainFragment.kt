package pzy64.xnotes.ui.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.launch
import pzy64.xnotes.R
import pzy64.xnotes.data.model.Note
import pzy64.xnotes.hide
import pzy64.xnotes.show
import pzy64.xnotes.ui.LastItemSpace
import pzy64.xnotes.ui.baseclasses.Pz64Fragment
import pzy64.xnotes.ui.screens.MenuType


class MainFragment : Pz64Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val placeHolderLoading = "LOADING.."
    private val placeholderAddNote = "ADD\nNOTE"

    private lateinit var noteAdapter: NotesAdapter

    private val noteClicked: (Note) -> Unit = { note ->
        viewModel.editMode(note)
        findNavController().navigate(R.id.destinationCreateNote)
    }

    private val selectedCallback: (Boolean) -> Unit = { selected ->
        if (selected) {
            viewModel.menuType.value = MenuType.TypeDelete
        } else {
            viewModel.menuType.value = MenuType.TypeNone
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            setupUi()
    }

    private fun setupUi() {

        placeHolder.text = placeHolderLoading
        placeHolder.show()

        placeHolder.setOnClickListener {
            if (placeHolder.text == placeholderAddNote) {
                viewModel.createMode()
                findNavController().navigate(R.id.destinationCreateNote)
            }
        }

        val layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        notesRecyclerView.layoutManager = layoutManager
        noteAdapter = NotesAdapter(noteClicked, selectedCallback)
        notesRecyclerView.adapter = noteAdapter
        val space = resources.getDimensionPixelSize(R.dimen.actionBarSize)
        notesRecyclerView.addItemDecoration(LastItemSpace(space, false))


        viewModel.getNotes().observe(viewLifecycleOwner, Observer {
            it?.let { notes ->
                if (notes.isNotEmpty()) {
                    noteAdapter.setNotes(notes)
                    placeHolder.hide()
                } else {
                    placeHolder.text = placeholderAddNote
                    placeHolder.show()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.actionDelete -> {
               noteAdapter.deleteSelectedNotes()


                launch {
                    val notes = viewModel.notes.filter {
                        it.isSelected
                    }
                    viewModel.moveToTrash(notes)
                    viewModel.menuType.value = MenuType.TypeNone

                    if (viewModel.notes.isEmpty()) {
                        placeHolder.text = placeholderAddNote
                        placeHolder.show()
                    }
                }

            }
        }

        return super.onOptionsItemSelected(item)
    }
}
