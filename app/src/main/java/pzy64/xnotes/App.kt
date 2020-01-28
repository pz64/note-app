package pzy64.xnotes

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        changeThemeFromPref()
    }


}