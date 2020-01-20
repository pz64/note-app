package pzy64.xnotes

import android.content.Context
import pzy64.xnotes.data.AppDb
import pzy64.xnotes.data.Repo
import pzy64.xnotes.ui.screens.Pz64ViewModel

object Injetor{

    fun provideVMFactory(appContext: Context): Pz64ViewModel.Factory   {
        val appDb = AppDb.getInstance(appContext)
        val repo = Repo.getInstance(appDb)
        val factory = Pz64ViewModel.Factory(repo)
        return factory
    }

}