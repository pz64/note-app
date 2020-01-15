package pzy64.xnotes.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import pzy64.xnotes.data.model.COLUMN_LASTUPDATED
import pzy64.xnotes.data.model.Note

@Dao
interface Dao {

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_LASTUPDATED DESC")
     suspend fun getNotes(): List<Note>

    @Update
    suspend fun update(note:Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note:Note)

    @Delete
    suspend fun delete(vararg notes: Note)

}