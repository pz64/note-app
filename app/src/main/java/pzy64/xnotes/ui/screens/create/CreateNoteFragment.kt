package pzy64.xnotes.ui.screens.create

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.create_note_fragment.*
import pzy64.xnotes.Injetor
import pzy64.xnotes.R
import pzy64.xnotes.databinding.CreateNoteFragmentBinding
import pzy64.xnotes.databinding.CreateNoteFragmentBindingImpl
import pzy64.xnotes.hideKeyboard
import pzy64.xnotes.showKeyboard
import pzy64.xnotes.ui.Colors
import pzy64.xnotes.ui.Fonts
import pzy64.xnotes.ui.baseclasses.Pz64Fragment
import pzy64.xnotes.ui.screens.Pz64ViewModel
import kotlin.math.hypot


class CreateNoteFragment : Pz64Fragment() {

    private lateinit var fonts: Fonts

    private lateinit var binding:CreateNoteFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fonts = Fonts(context)
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
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

            binding.viewModel = viewModel
            binding.lifecycleOwner = this

            setupUi()

    }


    private fun setupUi() {

        viewModel.editMode.observe(this, Observer { editMode ->
            if (editMode == false) {
                contentsEdittext.isFocusableInTouchMode = true
                titleEdittext.isFocusableInTouchMode = true
                titleEdittext.requestFocus()

                delayed(350) {
                    context?.showKeyboard(titleEdittext)
                }

            } else {
                contentsEdittext.isFocusableInTouchMode = false
                titleEdittext.isFocusableInTouchMode = false
            }
        })

        viewModel.currentColorIndex.observe(this, Observer {
            it?.let { index ->
                changeBg(Colors.bg(index, 0x3A))
            }
        })

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
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, titleEdittext.text)
                sharingIntent.putExtra(Intent.EXTRA_TEXT, contentsEdittext.text)
                startActivity(
                    Intent.createChooser(
                        sharingIntent,"Share via"
                    )
                )
            }
        }
        return false
    }

    override fun onPause() {
        super.onPause()
        context?.hideKeyboard(titleEdittext)
    }
}
