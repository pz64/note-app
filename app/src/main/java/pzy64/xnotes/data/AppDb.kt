package pzy64.xnotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pzy64.xnotes.data.model.Note

const val DB_NAME = "notes"
const val TABLE_NAME = "note"

@Database(entities = [Note::class], version = 1)
abstract class AppDb:RoomDatabase() {

    abstract fun userDao(): Dao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(appContext: Context) = instance ?: synchronized(this)  {
            instance ?: Room.databaseBuilder(appContext,AppDb::class.java, DB_NAME).build().also {
                instance = it
            }
        }
    }
}