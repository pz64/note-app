package pzy64.xnotes.ui.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.GlobalScope
import pzy64.xnotes.data.Repo
import pzy64.xnotes.data.model.Note

class MainViewModel(private val repo: Repo) : ViewModel() {

    class Factory(val repo: Repo) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(repo) as T
        }
    }

     fun getNotes(): LiveData<List<Note>> =
        liveData {
            emit(repo.getNotes())
        }

}
