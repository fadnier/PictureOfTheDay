package org.sochidrive.pod.ui.settings

import android.app.ProgressDialog.show
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_chips.*
import kotlinx.android.synthetic.main.settings_fragment.*
import org.sochidrive.pod.R

class SettingsFragment : Fragment() {

    val APP_PREFERENCES = "mysettings"
    val APP_PREFERENCES_THEME = "Theme"

    val APP_PREFERENCES_THEME_SPACE = "Space"
    val APP_PREFERENCES_THEME_MOON = "Moon"
    val APP_PREFERENCES_THEME_MARS = "Mars"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chipThemeMars.setOnClickListener {
            changeTheme(APP_PREFERENCES_THEME_MARS)
        }

        chipThemeMoon.setOnClickListener {
            changeTheme(APP_PREFERENCES_THEME_MOON)
        }

        chipThemeSpace.setOnClickListener {
            changeTheme(APP_PREFERENCES_THEME_SPACE)
        }
    }

    private fun changeTheme(themeName: String) {
        activity?.apply {
            val mySharedPreferences = this.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = mySharedPreferences.edit()
            editor.putString(APP_PREFERENCES_THEME, themeName)
            editor.apply()
        }
        activity?.recreate()
    }
}