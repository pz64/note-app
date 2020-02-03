package pzy64.xnotes.ui.screens.about


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marcoscg.licenser.LicenserDialog
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
            val url = "https://github.com/pz64/note-app"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        rateButton.setOnClickListener {
            val url = ""
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

    }




}
