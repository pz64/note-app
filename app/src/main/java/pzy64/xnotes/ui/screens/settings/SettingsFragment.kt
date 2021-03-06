package pzy64.xnotes.ui.screens.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import pzy64.xnotes.R
import pzy64.xnotes.changeThemeFromValue


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)

        preferenceManager
            .findPreference<ListPreference>(getString(R.string.pref_key_nightmode))
            ?.setOnPreferenceChangeListener { preference, newValue ->
                preference.context.changeThemeFromValue(newValue as String)
                true
            }
    }
}