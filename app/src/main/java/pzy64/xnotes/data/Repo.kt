package pzy64.xnotes.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pzy64.xnotes.data.model.Note

class Repo private constructor(private val appDb: AppDb) {

    companion object {
        @Volatile
        private var instance: Repo? = null

        fun getInstance(appDb: AppDb) = instance ?: synchronized(this) {
            instance ?: Repo(appDb).also {
                instance = it
            }
        }

    }

    suspend fun createNote(note: Note) {
        withContext(Dispatchers.IO) {
            appDb.userDao().insert(note)
        }
    }

    suspend fun updateNote(vararg note: Note) {
        withContext(Dispatchers.IO) {
            appDb.userDao().update(*note)
        }
    }


    suspend fun getNotes(): List<Note> = withContext(Dispatchers.IO) {
        appDb.userDao().getNotes()
    }

    suspend fun getTrash(): List<Note> = withContext(Dispatchers.IO) {
        appDb.userDao().getTrash()
    }

    suspend fun deleteNotes(vararg note: Note) {
        appDb.userDao().remove(*note)
    }

}