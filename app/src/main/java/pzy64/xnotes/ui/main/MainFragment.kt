package pzy64.xnotes.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pzy64.xnotes.R
import pzy64.xnotes.data.AppDb
import pzy64.xnotes.ui.baseclasses.Pz64Fragment

class MainFragment : Pz64Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        launch {
            withContext(Dispatchers.IO) {
                val db = AppDb.getInstance(this@MainFragment.context!!.applicationContext)
                val list = db.userDao().getNotes()
                Log.d("YYY", "List")

                for(i in list)
                    Log.d("YYY", "List: ${i.title}, ${i.content}, ${i.color}, ${i.font}")
            }
        }
    }

}
