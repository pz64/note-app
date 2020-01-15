package pzy64.xnotes.ui.create

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.animation.DecelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.create_note_fragment.*
import org.jetbrains.anko.toast
import pzy64.xnotes.R
import pzy64.xnotes.databinding.CreateNoteFragmentBinding
import pzy64.xnotes.ui.Colors
import pzy64.xnotes.ui.Fonts
import kotlin.math.hypot

class CreateNoteFragment : Fragment() {

    companion object {
        fun newInstance() = CreateNoteFragment()
    }

    private lateinit var viewModel: CreateNoteViewModel
    private lateinit var binding: CreateNoteFragmentBinding

    private lateinit var fonts: Fonts


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
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CreateNoteViewModel::class.java)
        binding.viewModel = viewModel
        setupUi()

    }

    private fun setupUi() {

        viewModel.currentColorIndex.observe(this, Observer {
            it?.let { index ->
                changeBg(Colors.COLORS[index])
            }
        })
    }

    private fun changeBg(color: Int) {

        colorRevealingLayout.visibility = View.GONE

        colorRevealingLayout.setCardBackgroundColor(color)

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
                val size = Colors.COLORS.size

                viewModel.currentColorIndex.value = (index + 1) % size

            }
            R.id.actionShareNote -> {
                context?.toast("share")
            }
        }
        return false
    }
}
