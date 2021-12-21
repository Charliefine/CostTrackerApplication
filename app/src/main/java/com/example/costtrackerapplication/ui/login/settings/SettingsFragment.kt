import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.costtrackerapplication.R
import java.util.*


class SettingsFragment : PreferenceFragmentCompat() {
    val THEME_APP = "Dark Mode"
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val listPreferenceTheme = findPreference<Preference>(getString(R.string.theme)) as ListPreference?
        listPreferenceTheme?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference) {
                val index = preference.findIndexOfValue(newValue.toString())
                val entryValue = preference.entryValues[index]
                if(entryValue=="default_theme"){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
                    val editor = sharedPref.edit()
                    editor.putString(THEME_APP, entryValue as String?)
                    editor.apply()
                }
                if(entryValue=="light_theme") {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
                    val editor = sharedPref.edit()
                    editor.putString(THEME_APP, entryValue as String?)
                    editor.apply()

                    }
                if(entryValue=="dark_theme"){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
                    val editor = sharedPref.edit()
                    editor.putString(THEME_APP, entryValue as String?)
                    editor.apply()

                }
            }
            true
        }

    val listPreferenceLang = findPreference<Preference>(getString(R.string.language)) as ListPreference?
    listPreferenceLang?.setOnPreferenceChangeListener { preference, newValue ->
        if (preference is ListPreference) {
            val index = preference.findIndexOfValue(newValue.toString())
            val entryValue = preference.entryValues[index]
            if(entryValue=="english_lang"){
                val locale = Locale("en")
                Locale.setDefault(locale)
                val resources = requireContext().resources
                val conf = resources.configuration
                conf.setLocale(locale)
                resources.updateConfiguration(conf, resources.displayMetrics)
                requireActivity().recreate()
            }
            if(entryValue=="polish_lang"){
                val locale = Locale("pl")
                Locale.setDefault(locale)
                val resources = requireContext().resources
                val conf = resources.configuration
                conf.setLocale(locale)
                resources.updateConfiguration(conf, resources.displayMetrics)
                requireActivity().recreate()
            }
        }
        true
    }

    }
}