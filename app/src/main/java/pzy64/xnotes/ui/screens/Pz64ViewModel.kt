package pzy64.xnotes.ui.screens

import androidx.lifecycle.*
import pzy64.xnotes.data.Repo
import pzy64.xnotes.data.model.Note

class Pz64ViewModel(private val repo: Repo) : ViewModel() {


    class Factory(val repo: Repo) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return Pz64ViewModel(repo) as T
        }
    }

    val currentColorIndex = MutableLiveData(0)

    val currentFontIndex = MutableLiveData(0)

    val title = MutableLiveData<String>()

    val content = MutableLiveData<String>()

    val currentNote = MutableLiveData<Note>(null)

    val noteSaved = MutableLiveData(false)

    val noteDismissed = MutableLiveData(false)

    val currentFABState = MutableLiveData<FABState>(FABState.CreateNote)

    val currentDestination = MutableLiveData<Int>()

    val editMode = MutableLiveData<Boolean>(false)

    init {

        currentNote.observeForever {

            title.value = it?.title ?: ""
            content.value = it?.content ?: ""
            currentColorIndex.value = it?.color ?: 0
            currentFontIndex.value = it?.font ?: 0

        }
    }

    suspend fun saveNote() {
        var update = false
        if (currentNote.value == null) {
            val note = Note(
                title = title.value?.trim() ?: "",
                content = content.value?.trim() ?: "",
                color = currentColorIndex.value ?: 0,
                font = currentFontIndex.value ?: 0,
                lastUpdated = System.currentTimeMillis()
            )
            currentNote.value = note

        } else {
            update = true
            currentNote.value?.also {
                it.title = title.value?.trim() ?: ""
                it.content = content.value?.trim() ?: ""
                it.color = currentColorIndex.value ?: 0
                it.font = currentFontIndex.value ?: 0
                it.lastUpdated = System.currentTimeMillis()
            }
        }

        currentNote.value?.let { note ->
            if (note.content.isNotEmpty() || note.title.isNotEmpty()) {
                if (update) {
                    repo.updateNote(note)
                } else {
                    repo.createNote(note)
                }
                noteSaved.postValue(true)
            } else {
                noteDismissed.value = true
            }
        }
    }

    fun getNotes(): LiveData<List<Note>> =
        liveData {
            emit(repo.getNotes())
        }

}
