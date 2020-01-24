package pzy64.xnotes.ui.screens

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.menu_sheet.*
import kotlinx.coroutines.launch
import pzy64.xnotes.Injetor
import pzy64.xnotes.R
import pzy64.xnotes.ui.baseclasses.Pz64Activity
import pzy64.xnotes.ui.screens.main.MainFragmentDirections
import pzy64.xnotes.ui.screens.trash.TrashFragmentDirections


class MainActivity : Pz64Activity() {

    private lateinit var navController: NavController
    private lateinit var viewModel: Pz64ViewModel
    private lateinit var menuBehavior: BottomSheetBehavior<View>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(bottomAppBar)

        val factory = Injetor.provideVMFactory(applicationContext)
        viewModel = ViewModelProvider(this, factory)[Pz64ViewModel::class.java]


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

        menuBehavior = BottomSheetBehavior.from(menuSheet)
        menuBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        settingsButton.setOnClickListener {
            when (viewModel.currentDestination.value) {
                R.id.destinationTrash -> {
                    val destination = TrashFragmentDirections.openSettings()
                    navController.navigate(destination)
                }
                R.id.destinationMainFragment -> {
                    val destination = MainFragmentDirections.openSettings()
                    navController.navigate(destination)
                }
            }
        }

        homeOrtrashButton.setOnClickListener {

            menuBehavior.state = BottomSheetBehavior.STATE_HIDDEN


            when (viewModel.currentDestination.value) {
                R.id.destinationTrash -> {
                    val destination = TrashFragmentDirections.openHome()
                    navController.navigate(destination)
                }
                R.id.destinationMainFragment -> {
                    val destination = MainFragmentDirections.openTrash()
                    navController.navigate(destination)
                }
            }
        }

        bottomAppBar.setNavigationOnClickListener {
            menuBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }

        hideMenuSheet.setOnClickListener {
            menuBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        viewModel.menuType.observe(this, Observer {
            it?.let {
                bottomAppBar.menu.clear()
                when (it) {
                    MenuType.TypeNone -> {

                    }
                    MenuType.TypeCreate -> {
                        menuInflater.inflate(
                            R.menu.create_edit_note_menu,
                            bottomAppBar.menu
                        )
                    }
                    MenuType.TypeDelete -> {
                        menuInflater.inflate(
                            R.menu.delete_note_menu,
                            bottomAppBar.menu
                        )
                    }
                    MenuType.TypeDeletePermanent -> {
                        menuInflater.inflate(
                            R.menu.remove_restore_menu,
                            bottomAppBar.menu
                        )
                    }
                }
            }
        })

        viewModel.currentDestination.observe(this, Observer {
            it?.let {
                when (it) {
                    R.id.destinationMainFragment -> {
                        homeOrtrashButton.text = "Trash"
                        homeOrtrashButton.setIconResource(R.drawable.ic_delete)
                        bottomAppBar.setNavigationIcon(R.drawable.ic_menu)

                        viewModel.currentFABState.value = FABState.CreateNote
                    }
                    R.id.destinationTrash -> {
                        homeOrtrashButton.text = "Home"
                        homeOrtrashButton.setIconResource(R.drawable.ic_home)
                        bottomAppBar.setNavigationIcon(R.drawable.ic_menu)

                        viewModel.currentFABState.value = FABState.Hidden
                    }
                    R.id.destinationCreateNote -> {
                        menuBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                        bottomAppBar.navigationIcon = null
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
                                        R.color.colorPrimary
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
                                    viewModel.menuType.value = MenuType.TypeCreate
                                }
                            })
                        }
                    }
                    FABState.Hidden -> {
                        delayed {
                            faButton.hide()
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
                        launch { viewModel.saveNote() }
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
        viewModel.menuType.value = MenuType.TypeNone
        navController.popBackStack()
    }

    override fun onBackPressed() {
        when (viewModel.currentDestination.value) {
            R.id.destinationCreateNote -> {
                navController.popBackStack()
            }
            R.id.destinationSettings -> {
                navController.popBackStack()
            }
            R.id.destinationTrash -> {
                super.onBackPressed()
            }
            R.id.destinationMainFragment -> {
                super.onBackPressed()
            }
        }
    }
}
