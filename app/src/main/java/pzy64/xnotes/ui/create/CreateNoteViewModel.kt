package pzy64.xnotes.ui.create

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
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

    @Bindable
    val title = MutableLiveData<String>()

    @Bindable
    val content = MutableLiveData<String>()

    private val currentNote = MutableLiveData<Note>()

    val noteSaved = MutableLiveData(false)

    suspend fun saveNote() {

        val note = Note(
            0,
            title.value ?: "",
            content.value ?: "",
            currentColorIndex.value ?: 0,
            currentFontIndex.value ?: 0,
            System.currentTimeMillis()
        )
        Log.d("YYY", "saved: ${note.title}, ${note.content}, ${note.color}, ${note.font}")
        currentNote.value = note

        currentNote.value?.let { note ->
            repo.createNote(note)
            noteSaved.postValue(true)
        }
    }


}
