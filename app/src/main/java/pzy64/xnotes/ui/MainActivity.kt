package pzy64.xnotes.ui

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.main_activity.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pzy64.xnotes.R
import pzy64.xnotes.data.eventbusmodel.Action
import pzy64.xnotes.data.eventbusmodel.FabButtonActionModel
import pzy64.xnotes.data.eventbusmodel.ReplyModel
import pzy64.xnotes.delayed
import pzy64.xnotes.ui.screens.main.MainFragmentDirections


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainActivityViewModel

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(bottomAppBar)

        navController = Navigation.findNavController(
            this,
            R.id.navigationController
        )

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        setupUi()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ReplyModel) {
        when (event.action) {
            Action.NOTE_SAVED -> {
                navigateBacktOMainScreen()
            }
            Action.NOTE_DISMISSED -> {
                navigateBacktOMainScreen()
            }
        }
    }


    private fun setupUi() {


        faButton.setImageResource(R.drawable.ic_add_note)
        faButton.imageTintList = null

        faButton.setOnClickListener {
            when (navController.currentDestination?.id) {

                R.id.destinationMainFragment -> {
                    navigateToCreateNote()
                }
                R.id.destinationCreateNote -> {
                    EventBus.getDefault().post(FabButtonActionModel(Action.SAVE_NOTE))
                }
            }
        }
    }

    private fun navigateToCreateNote() {
        bottomAppBar.menu.clear()

        faButton.hide()

        delayed {
            faButton.setImageResource(R.drawable.ic_save_note)
            faButton.imageTintList = null
            faButton.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this,
                        R.color.amber_800
                    )
                )

            faButton.show(object : FloatingActionButton.OnVisibilityChangedListener() {
                override fun onShown(fab: FloatingActionButton?) {
                    super.onShown(fab)
                    menuInflater.inflate(
                        R.menu.create_edit_note_menu,
                        bottomAppBar.menu
                    )
                }

            })
        }

        val destination = MainFragmentDirections.createNote()
        navController.navigate(destination)
    }

    private fun navigateBacktOMainScreen() {
        bottomAppBar.menu.clear()

        faButton.hide()

        delayed {
            faButton.setImageResource(R.drawable.ic_add_note)
            faButton.imageTintList = null
            faButton.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this,
                        R.color.grey_50
                    )
                )
            faButton.show()
        }
        navController.popBackStack()
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.destinationCreateNote -> {
                EventBus.getDefault().post(FabButtonActionModel(Action.SAVE_NOTE))
            }
            R.id.destinationMainFragment -> {
                super.onBackPressed()
            }
        }

    }

}
