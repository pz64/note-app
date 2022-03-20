package pzy64.xnotes.ui.screens

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pzy64.xnotes.data.Repo
import pzy64.xnotes.data.model.NOTE_ACTIVE
import pzy64.xnotes.data.model.NOTE_DELETED
import pzy64.xnotes.data.model.Note

class Pz64ViewModel(private val repo: Repo) : ViewModel() {


    class Factory(val repo: Repo) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return Pz64ViewModel(repo) as T
        }
    }

    val currentColorIndex = MutableLiveData(0)

    val currentFontIndex = MutableLiveData(0)

    val title = MutableLiveData<String>()

    val content = MutableLiveData<String>()

    val currentNote = MutableLiveData<Note?>(null)
    var currentNoteBeforeEdit: Note? = null

    val noteSaved = MutableLiveData(false)

    val noteDismissed = MutableLiveData(false)

    val currentFABState = MutableLiveData<FABState>(FABState.CreateNote)

    val currentDestination = MutableLiveData<Int>()

    val editMode = MutableLiveData<Boolean>(false)

    var notes = mutableListOf<Note>()

    val menuType = MutableLiveData(MenuType.TypeNone)


    init {

        currentNote.observeForever {

            title.value = it?.title ?: ""
            content.value = it?.content ?: ""
            currentColorIndex.value = it?.color ?: 0
            currentFontIndex.value = it?.font ?: 0

        }
    }

    fun saveNote() = viewModelScope.launch(Dispatchers.IO) {

        if (currentNote.value == null) {

            val note = Note(
                title = title.value?.trim() ?: "",
                content = content.value?.trim() ?: "",
                color = currentColorIndex.value ?: 0,
                font = currentFontIndex.value ?: 0,
                lastUpdated = System.currentTimeMillis()
            )

            if (note.content.isNotEmpty() || note.title.isNotEmpty()) {
                repo.createNote(note)
                noteSaved.postValue(true)
            } else {
                noteDismissed.postValue(true)
            }
            currentNote.postValue(note)
        } else {
            currentNote.value?.also {
                it.title = title.value?.trim() ?: ""
                it.content = content.value?.trim() ?: ""
                it.color = currentColorIndex.value ?: 0
                it.font = currentFontIndex.value ?: 0
                it.lastUpdated = System.currentTimeMillis()
            }

            if (  !currentNote.value?.content.isNullOrEmpty() ||   !currentNote.value?.title.isNullOrEmpty()) {
                    if (isNoteModified(  currentNote.value!!))
                        repo.updateNote(  currentNote.value!!)
                noteSaved.postValue(  true)
            } else {
                noteDismissed.postValue(true)
            }
        }
    }

    private fun isNoteModified(note: Note): Boolean {
        note
        currentNoteBeforeEdit
        if (note.color == currentNoteBeforeEdit?.color &&
            note.content == currentNoteBeforeEdit?.content &&
            note.font == currentNoteBeforeEdit?.font &&
            note.title == currentNoteBeforeEdit?.title &&
            note.id == currentNoteBeforeEdit?.id
        )
            return false
        return true

    }

    fun getNotes(): LiveData<List<Note>> =
        liveData {
            val notes = repo.getNotes()
            this@Pz64ViewModel.notes = notes.toMutableList()
            emit(notes)
        }

    fun getTrash(): LiveData<List<Note>> =
        liveData {
            val notes = repo.getTrash()
            this@Pz64ViewModel.notes = notes.toMutableList()
            emit(notes)
        }

    suspend fun moveToTrash(note: List<Note>) {
        for (i in note) {
            notes.remove(i)
            i.deleted = NOTE_DELETED
        }
        repo.updateNote(*note.toTypedArray())
    }

    suspend fun restoreFromTrash(note: List<Note>) {
        for (i in note) {
            i.deleted  = NOTE_ACTIVE
            notes.remove(i)
        }
        repo.updateNote(*note.toTypedArray())
    }

    suspend fun deleteNote(note: List<Note>) {
        for (i in note)
            notes.remove(i)
        repo.deleteNotes(*note.toTypedArray())
    }

    fun createMode() {
        editMode.value = false
        currentNoteBeforeEdit = null
        currentNote.value = null
    }

    fun editMode(note: Note) {
        currentNoteBeforeEdit = note.copy()
        currentNote.value = note
        editMode.value = true
    }
}
