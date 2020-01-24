package pzy64.xnotes.ui.screens.trash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
import pzy64.xnotes.ui.screens.MenuType
import pzy64.xnotes.ui.screens.Pz64ViewModel
import pzy64.xnotes.ui.screens.main.NotesAdapter

class TrashFragment : Pz64Fragment() {

    companion object {
        fun newInstance() = TrashFragment()
    }

    private lateinit var viewModel: Pz64ViewModel

    private lateinit var factory: Pz64ViewModel.Factory

    private val placeHolderLoading = "LOADING.."
    private val placeholderEmpty = "TRASH\nEMPTY"
    private lateinit var noteAdapter: NotesAdapter

    private val noteClicked: (Note) -> Unit = { note ->
        viewModel.currentNote.value = note
        viewModel.editMode.value = true
        findNavController().navigate(R.id.destinationCreateNote)

    }

    private val selectedCallback: (Boolean) -> Unit = { selected ->
        if (selected) {
            viewModel.menuType.value = MenuType.TypeDeletePermanent
        } else {
            viewModel.menuType.value = MenuType.TypeNone
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        factory = Injetor.provideVMFactory(context.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.trash_fragment, container, false)
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

        val layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        notesRecyclerView.layoutManager = layoutManager
        val space = resources.getDimensionPixelSize(R.dimen.actionBarSize)
        notesRecyclerView.addItemDecoration(LastItemSpace(space, false))
        noteAdapter = NotesAdapter(noteClicked, selectedCallback)

        launch {
            viewModel.getTrash().observe(this@TrashFragment, Observer {
                it?.let { notes ->
                    if (notes.isNotEmpty()) {
                        noteAdapter.data = notes.toMutableList()
                        notesRecyclerView.adapter = noteAdapter
                        placeHolder.hide()
                    } else {
                        placeHolder.text = placeholderEmpty
                        placeHolder.show()
                    }
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.actionDeleteForever -> {
                if (attached) {
                    AlertDialog.Builder(context!!)
                        .setTitle("Confirm Deletion")
                        .setMessage("Deleted note can't be retrieved later.")
                        .setPositiveButton(android.R.string.ok) { dialog, which ->

                            launch {
                                val notes = viewModel.notes.filter {
                                    it.isSelected
                                }
                                viewModel.deleteNote(notes)
                                viewModel.menuType.value = MenuType.TypeNone
                                noteAdapter.deleteSelectedNotes()
                                if (viewModel.notes.isEmpty()) {
                                    placeHolder.text = placeholderEmpty
                                    placeHolder.show()
                                }
                            }
                        }.setNegativeButton(android.R.string.cancel) { dialog, which ->
                            dialog.dismiss()
                        }
                        .setOnDismissListener {
                            noteAdapter.deSelectAll()
                            viewModel.menuType.value = MenuType.TypeNone
                        }.show()
                }

            }
            R.id.actionRestore -> {
                noteAdapter.deleteSelectedNotes()
                launch {
                    val notes = viewModel.notes.filter {
                        it.isSelected
                    }
                    viewModel.restoreFromTrash(notes)
                    if (viewModel.notes.isEmpty()) {
                        placeHolder.text = placeholderEmpty
                        placeHolder.show()
                    }
                    viewModel.menuType.value = MenuType.TypeNone
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
