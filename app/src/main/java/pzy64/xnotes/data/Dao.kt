package pzy64.xnotes.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import pzy64.xnotes.data.model.*

@Dao
interface Dao {

    @Query("SELECT * FROM $TABLE_NAME " +
            "WHERE $COLUMN_DELETED = $NOTE_ACTIVE " +
            "ORDER BY $COLUMN_LASTUPDATED DESC")
     suspend fun getNotes(): List<Note>

    @Query("SELECT * FROM $TABLE_NAME " +
            "WHERE $COLUMN_DELETED = $NOTE_DELETED " +
            "ORDER BY $COLUMN_LASTUPDATED DESC")
    suspend fun getTrash(): List<Note>

    @Update
    suspend fun update(vararg note:Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note:Note)

    @Delete
    suspend fun remove(vararg notes: Note)

}