package world.gregs.image.palette.controllers

import de.androidpit.colorthief.ColorThief
import javafx.scene.paint.Color
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tornadofx.Controller
import world.gregs.image.palette.models.HSLColour
import java.awt.image.BufferedImage
import java.io.File

class Grab : Controller() {
    //UI
    val colours = Colours()
    val preview = Preview()
    //Settings
    val settings = UserPreferences()

    private var image: BufferedImage? = null

    /**
     * Grabs palette from the image
     * @param file The image to retrieve from
     */
    fun palette(file: File) {
        //Process off UI thread
        GlobalScope.launch {
            //Remove current image
            clear()
            //Load new image
            image = preview.load(file) ?: return@launch
            //Extract palette
            process()
        }
    }

    /**
     * Processes the current image, adding to the [Colours] palette
     */
    private fun process() {
        if (image == null) {
            return
        }

        fun IntArray.toHsl(): HSLColour =
            HSLColour(Color.rgb(this[0], this[1], this[2]))

        //Settings
        val quality = settings.quality()
        val ignore = settings.ignoreWhite()
        val count = settings.colourCount()

        //Add dominant colour
        colours.add(ColorThief.getColor(image, quality, ignore).toHsl())

        //Add remaining palette according to settings
        if (count > 1) {
            for (rgb in ColorThief.getPalette(image, count, quality, ignore)) {
                colours.add(rgb.toHsl())
            }
        }
    }

    /**
     * Re-calculates the colour palette based on the current settings
     */
    fun reload() {
        colours.clear()
        process()
    }

    /**
     * Clears the image and palette from the ui
     */
    fun clear() {
        preview.clear()
        colours.clear()
        image = null
    }
}