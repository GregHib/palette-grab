package world.gregs.image.palette.views

import tornadofx.View
import tornadofx.borderpane

/**
 * The main view with top [Menu] and [Content] or [Settings]
 */
class MainView : View("PaletteGrab") {

    override val root= borderpane {
        top(Menu::class)
        center(Content::class)
    }
}