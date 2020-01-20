package pzy64.xnotes.ui.screens

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.launch
import pzy64.xnotes.Injetor
import pzy64.xnotes.R
import pzy64.xnotes.delayed
import pzy64.xnotes.ui.baseclasses.Pz64Activity
import pzy64.xnotes.ui.screens.main.MainFragmentDirections


class MainActivity : Pz64Activity() {

    private lateinit var navController: NavController
    private lateinit var viewModel: Pz64ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(bottomAppBar)

        val factory = Injetor.provideVMFactory(applicationContext)
        viewModel = ViewModelProviders.of(this, factory).get(Pz64ViewModel::class.java)

        navController = Navigation.findNavController(
            this,
            R.id.navigationController
        )

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            viewModel.currentDestination.value = destination.id
        }
        setupUi()

    }

    private fun setupUi() {

        viewModel.currentDestination.observe(this, Observer {
            it?.let {
                when (it) {
                    R.id.destinationMainFragment -> {
                        viewModel.currentFABState.value = FABState.CreateNote
                    }
                    R.id.destinationCreateNote -> {
                        viewModel.editMode.observe(this, Observer {
                            if (viewModel.editMode.value == true)
                                viewModel.currentFABState.value = FABState.EditNote
                            else viewModel.currentFABState.value = FABState.SaveNote
                        })
                    }
                }
            }
        })

        viewModel.currentFABState.observe(this, Observer {
            it?.let { state ->

                when (state) {
                    FABState.CreateNote -> {
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
                    }

                    FABState.EditNote -> {
                        faButton.hide()
                        delayed {
                            faButton.setImageResource(R.drawable.ic_edit_note)
                            faButton.imageTintList = null
                            faButton.backgroundTintList =
                                ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        this,
                                        R.color.amber_800
                                    )
                                )
                            faButton.show()
                        }
                    }

                    FABState.SaveNote -> {
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
                            faButton.show(object :
                                FloatingActionButton.OnVisibilityChangedListener() {
                                override fun onShown(fab: FloatingActionButton?) {
                                    super.onShown(fab)
                                    if (bottomAppBar.menu.isEmpty())
                                        menuInflater.inflate(
                                            R.menu.create_edit_note_menu,
                                            bottomAppBar.menu
                                        )
                                }
                            })
                        }
                    }
                }
            }
        })

        viewModel.noteSaved.observe(this, Observer {
            if (it == true) {
                navigateBacktOMainScreen()
            }
        })

        viewModel.noteDismissed.observe(this, Observer {
            if (it == true) {
                navigateBacktOMainScreen()
            }
        })

        faButton.setOnClickListener {
            when (viewModel.currentDestination.value) {
                R.id.destinationMainFragment -> {
                    viewModel.editMode.value = false
                    viewModel.currentNote.value = null
                    navigateToCreateNote()
                }
                R.id.destinationCreateNote -> {
                    if (viewModel.editMode.value == true) {
                        viewModel.editMode.value = false
                    } else {
                        launch {
                            viewModel.saveNote()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToCreateNote() {
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
        when (viewModel.currentDestination.value) {
            R.id.destinationCreateNote -> {
                navController.popBackStack()
            }
            R.id.destinationMainFragment -> {
                super.onBackPressed()
            }
        }
    }
}
