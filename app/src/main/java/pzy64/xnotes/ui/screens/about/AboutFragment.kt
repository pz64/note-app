package pzy64.xnotes.ui.screens.about


import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_about.*
import pzy64.xnotes.BuildConfig

import pzy64.xnotes.R

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        versionNumber.text = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"

        viewGithubRepoButton.setOnClickListener {

        }
    }


}
