package pzy64.xnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import pzy64.xnotes.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navConteroller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navConteroller = Navigation.findNavController(this, R.id.navigationController)

        setupUi()
    }

    private fun setupUi()   {

    }
}
