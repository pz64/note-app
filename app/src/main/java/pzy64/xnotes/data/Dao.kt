package pzy64.xnotes.data

import androidx.room.*
import androidx.room.Dao
import pzy64.xnotes.data.model.Note

@Dao
interface Dao {

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getNotes(): List<Note>

    @Update
    suspend fun update(note:Note)

    @Insert
    suspend fun insert(note:Note)

    @Delete
    suspend fun delete(note: Note)


}