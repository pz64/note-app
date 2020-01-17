package pzy64.xnotes.ui.screens.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pzy64.xnotes.data.Repo
import pzy64.xnotes.data.model.Note

class CreateNoteViewModel(private val repo: Repo) : ViewModel() {


    class Factory(val repo: Repo) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CreateNoteViewModel(repo) as T
        }
    }

    val currentColorIndex = MutableLiveData(0)

    val currentFontIndex = MutableLiveData(0)

    val title = MutableLiveData<String>()

    val content = MutableLiveData<String>()

    val currentNote = MutableLiveData<Note>()

    val noteSaved = MutableLiveData(false)
    val noteDismissed = MutableLiveData(false)

    init {

        currentNote.observeForever {
            it?.let {
                title.value = it.title
                content.value = it.content
                currentColorIndex.value = it.color
                currentFontIndex.value = it.font
            }
        }
    }

    suspend fun saveNote() {

        val note = Note(
            0,
            title.value?.trim() ?: "",
            content.value?.trim() ?: "",
            currentColorIndex.value ?: 0,
            currentFontIndex.value ?: 0,
            System.currentTimeMillis()
        )
        currentNote.value = note

        currentNote.value?.let { note ->
            if (note.content.isNotEmpty() || note.title.isNotEmpty()) {
                repo.createNote(note)
                noteSaved.postValue(true)
            } else {
                noteDismissed.value = true
            }
        }
    }


}
