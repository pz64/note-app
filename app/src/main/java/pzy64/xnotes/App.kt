package pzy64.xnotes

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import pzy64.xnotes.data.AppDb
import pzy64.xnotes.data.DB_NAME

class App: Application()  {

    lateinit var notesDb: AppDb
    override fun onCreate() {
        super.onCreate()
    }
}