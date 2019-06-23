package world.gregs.image.palette.controllers

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import world.gregs.image.palette.models.Colour
import world.gregs.image.palette.models.HSLColour
import java.util.*

/**
 * Palette of grabbed colours ready for display
 */
class Colours {

    val list: ObservableList<Colour> = FXCollections.observableList(LinkedList<Colour>())

    fun add(colour: HSLColour) = add(Colour(colour))

    fun add(colour: Colour) = GlobalScope.launch(Dispatchers.Main) {
        if (!list.contains(colour)) {
            list.add(colour)
        }
    }

    fun clear() = GlobalScope.launch(Dispatchers.Main) {
        list.clear()
    }

}