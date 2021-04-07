package org.sochidrive.pod

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sochidrive.pod.ui.picture.PictureOfTheDayFragment


class MainActivity : AppCompatActivity() {

    val APP_PREFERENCES = "mysettings"
    val APP_PREFERENCES_THEME = "Theme"
    val APP_PREFERENCES_THEME_SPACE = "Space"
    val APP_PREFERENCES_THEME_MOON = "Moon"
    val APP_PREFERENCES_THEME_MARS = "Mars"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mySharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        if(mySharedPreferences.contains(APP_PREFERENCES_THEME)) {
            when(mySharedPreferences.getString(APP_PREFERENCES_THEME,APP_PREFERENCES_THEME_SPACE)) {
                APP_PREFERENCES_THEME_MARS -> setTheme(R.style.AppThemeMars)
                APP_PREFERENCES_THEME_SPACE -> setTheme(R.style.AppThemeSpace)
                APP_PREFERENCES_THEME_MOON -> setTheme(R.style.AppThemeMoon)
            }

        }
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                    .commitNow()
        }
    }
}