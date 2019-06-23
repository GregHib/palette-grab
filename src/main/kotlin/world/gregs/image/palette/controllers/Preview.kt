package world.gregs.image.palette.controllers

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import javafx.scene.layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import world.gregs.image.palette.app.Styles
import java.awt.image.BufferedImage
import java.io.File

/**
 * Preview area controller
 */
class Preview {

    val preview = SimpleObjectProperty<Image>()
    val loading = SimpleBooleanProperty(false)
    val draggable = SimpleBooleanProperty(true)
    val border = SimpleObjectProperty(DEFAULT_BORDER)

    /**
     * Loads an image into the preview and returns it's buffer
     * @param file The file of the image to load
     * @return null if unsupported file type otherwise [BufferedImage] the buffered image
     */
    fun load(file: File): BufferedImage? {
        //Display progress indicator
        loading.set(true)
        //Hide drop message
        draggable.set(false)
        //Load image
        val image = Image(file.toURI().toString())
        //Handle errors
        if (image.isError) {
            error()
            image.exception.printStackTrace()
            return null
        }
        //Set preview image
        preview(image)
        //Return buffered image
        return SwingFXUtils.fromFXImage(image, null)
    }

    /**
     * Loads an image into the preview ui
     */
    private fun preview(image: Image) = GlobalScope.launch(Dispatchers.Main) {
        preview.set(image)
        loading.set(false)
    }

    /**
     * Clears the preview area and resets to the default "drag image here" message
     */
    fun clear() {
        //Remove progress indicator
        loading.set(false)
        draggable.set(true)
        //Set default border
        border.set(DEFAULT_BORDER)
        //Clear preview image
        preview.set(null)
    }

    /**
     * Sets "drag image here" message to a red border to show invalid file type
     */
    fun error() = GlobalScope.launch(Dispatchers.Main) {
        //Set red border
        border.set(ERROR_BORDER)
        //Stop loading
        loading.set(false)
        draggable.set(true)
    }

    companion object {
        private val ERROR_BORDER =
            Border(BorderStroke(Styles.buttonHighlightColour, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT))
        private val DEFAULT_BORDER =
            Border(BorderStroke(Styles.textColour, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT))
    }
}