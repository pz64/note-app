package pzy64.xnotes

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.toast
import pzy64.xnotes.ui.main.MainFragmentDirections


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

//    private  var menu:Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(bottomAppBar)

        navController = Navigation.findNavController(this, R.id.navigationController)

        setupUi()
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        return true
//    }

    private fun setupUi() {

        faButton.setImageResource(R.drawable.ic_add_note)
        faButton.imageTintList = null

        faButton.setOnClickListener {
            when (navController.currentDestination?.id) {

                R.id.destinationMainFragment -> {

                    bottomAppBar.menu.clear()
                    faButton.hide()

                    delayed {
                        faButton.setImageResource(R.drawable.ic_save_note)
                        faButton.imageTintList = null
                        faButton.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.amber_800))

                        faButton.show(object : FloatingActionButton.OnVisibilityChangedListener() {
                            override fun onShown(fab: FloatingActionButton?) {
                                super.onShown(fab)
                                menuInflater.inflate(R.menu.create_edit_note_menu, bottomAppBar.menu)
                            }

                        })
                    }

                    val destination = MainFragmentDirections.createNote()
                    navController.navigate(destination)
                }
                R.id.destinationCreateNote -> {

                    bottomAppBar.menu.clear()

                    faButton.hide()

                    delayed {
                        faButton.setImageResource(R.drawable.ic_add_note_2)
                        faButton.imageTintList = null
                        faButton.backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey_50))
                        faButton.show()
                    }
                    navController.popBackStack()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionChangeColor -> {
                toast("change color")
            }
            R.id.actionShareNote -> {
                toast("share")
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
