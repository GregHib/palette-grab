package world.gregs.image.palette.controllers

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import java.util.prefs.Preferences

/**
 * Storing and retrieval of user preferences
 */
class UserPreferences {
    private val preferences: Preferences = Preferences.userNodeForPackage(UserPreferences::class.java)

    //Settings
    val quality = SimpleIntegerProperty(preferences.getInt(QUALITY, 1))
    val colourCount = SimpleIntegerProperty(preferences.getInt(COLOUR_COUNT, 10))
    val ignoreWhite = SimpleBooleanProperty(preferences.getBoolean(IGNORE_WHITE, true))
    val skipAnimation = SimpleBooleanProperty(preferences.getBoolean(SKIP_ANIMATION, false))

    fun save() {
        preferences.putInt(QUALITY, quality())
        preferences.putInt(COLOUR_COUNT, colourCount())
        preferences.putBoolean(IGNORE_WHITE, ignoreWhite())
        preferences.putBoolean(SKIP_ANIMATION, skipAnimation())
    }

    fun quality(): Int = quality.get()

    fun colourCount(): Int = colourCount.get()

    fun ignoreWhite(): Boolean = ignoreWhite.get()

    fun skipAnimation(): Boolean = skipAnimation.get()

    companion object {
        private const val QUALITY = "quality"
        private const val COLOUR_COUNT = "colourCount"
        private const val IGNORE_WHITE = "ignoreWhite"
        private const val SKIP_ANIMATION = "skipAnimation"
    }

}