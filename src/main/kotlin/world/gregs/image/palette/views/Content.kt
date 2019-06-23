package world.gregs.image.palette.views

import tornadofx.View
import tornadofx.borderpane

/**
 * The main content view
 */
class Content : View() {

    override val root = borderpane {
        center(PreviewBox::class)
        right(Palette::class)
    }

}