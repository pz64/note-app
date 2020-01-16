package pzy64.xnotes

import android.content.Context
import pzy64.xnotes.data.AppDb
import pzy64.xnotes.data.Repo
import pzy64.xnotes.ui.create.CreateNoteViewModel
import pzy64.xnotes.ui.main.MainViewModel

object Injetor{

    fun provideCreateNoteVMFactory(appContext: Context): CreateNoteViewModel.Factory   {
        val appDb = AppDb.getInstance(appContext)
        val repo = Repo.getInstance(appDb)
        val factory = CreateNoteViewModel.Factory(repo)
        return factory
    }

    fun provideMainVMFactory(appContext: Context): MainViewModel.Factory   {
        val appDb = AppDb.getInstance(appContext)
        val repo = Repo.getInstance(appDb)
        val factory = MainViewModel.Factory(repo)
        return factory
    }
}