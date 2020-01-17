package pzy64.xnotes.ui.screens.create

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.create_note_fragment.*
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast
import pzy64.xnotes.Injetor
import pzy64.xnotes.R
import pzy64.xnotes.data.eventbusmodel.Action
import pzy64.xnotes.data.eventbusmodel.FabButtonActionModel
import pzy64.xnotes.data.eventbusmodel.ReplyModel
import pzy64.xnotes.databinding.CreateNoteFragmentBinding
import pzy64.xnotes.delayed
import pzy64.xnotes.ui.Colors
import pzy64.xnotes.ui.Fonts
import pzy64.xnotes.ui.baseclasses.Pz64Fragment
import kotlin.math.hypot

class CreateNoteFragment : Pz64Fragment() {


    private lateinit var viewModel: CreateNoteViewModel
    private lateinit var binding: CreateNoteFragmentBinding

    private lateinit var fonts: Fonts
    private lateinit var factory: CreateNoteViewModel.Factory

    private var editMode = true

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fonts = Fonts(context)
        factory = Injetor.provideCreateNoteVMFactory(context.applicationContext)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_note_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(CreateNoteViewModel::class.java)

        CreateNoteFragmentArgs.fromBundle(arguments?: Bundle()).also {
            viewModel.currentNote.value = it.note
            editMode = it.editMode
        }

        binding.viewModel = viewModel

        setupUi()

    }

    private fun setupUi() {

        titleEdittext.requestFocus()

        delayed(600) {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(titleEdittext, InputMethodManager.SHOW_IMPLICIT)
        }

        viewModel.currentColorIndex.observe(this, Observer {
            it?.let { index ->
                changeBg(Colors.bg(index, 0x3A))
            }
        })
        viewModel.noteSaved.observe(this, Observer {
            if (it == true) {
                EventBus.getDefault().post(ReplyModel(Action.NOTE_SAVED))
            }
        })
        viewModel.noteDismissed.observe(this, Observer {
            if (it == true) {
                EventBus.getDefault().post(ReplyModel(Action.NOTE_DISMISSED))
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun messageFromMainActivity(event: FabButtonActionModel) {
        when (event.action) {
            Action.SAVE_NOTE -> {
                launch {
                    viewModel.saveNote()
                }
            }

        }
    }

    private fun changeBg(color: Int) {

        colorRevealingLayout.visibility = View.GONE

        colorRevealingLayoutBg.setBackgroundColor(color)

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)

        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels


        val pivotX = 0
        val pivotY = screenHeight

        val startRadius = 0f
        val endRadius = hypot(
            screenWidth.toDouble(),
            screenHeight.toDouble()
        ).toFloat()

        val animator = ViewAnimationUtils.createCircularReveal(
            colorRevealingLayout,
            pivotX,
            pivotY,
            startRadius,
            endRadius
        ).apply {
            duration = 500L
            interpolator = DecelerateInterpolator(2f)
        }

        colorRevealingLayout.visibility = View.VISIBLE


        animator.start()
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                if (context != null) {
                    containerLayout.setBackgroundColor(color)
                    colorRevealingLayout.visibility = View.GONE
                }
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionChangeFont -> {

                val index = viewModel.currentFontIndex.value ?: 0
                val size = fonts.FONTS.size

                viewModel.currentFontIndex.value = (index + 1) % size
            }

            R.id.actionChangeColor -> {
                val index = viewModel.currentColorIndex.value ?: 0
                val size = Colors.size

                viewModel.currentColorIndex.value = (index + 1) % size
            }
            R.id.actionShareNote -> {
                context?.toast("share")
            }
        }
        return false
    }

}
