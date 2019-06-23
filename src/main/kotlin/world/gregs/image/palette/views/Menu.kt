package world.gregs.image.palette.views

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Priority
import tornadofx.*
import world.gregs.image.palette.app.Styles.Companion.logo
import world.gregs.image.palette.app.Styles.Companion.menu
import world.gregs.image.palette.controllers.Grab

class Menu : View() {

    private val grab: Grab by inject()
    private val content: Content by inject()
    private val settings: Settings by inject()

    @Suppress("UsePropertyAccessSyntax")
    override val root = toolbar {
        addClass(menu)

        hbox {
            addClass(logo)
            imageview("images/logo.png", true)
            hgrow = Priority.ALWAYS//Push other tools to right side
        }

        button(graphic = ImageView(Image("images/clear.png", true))) {
            setOnAction {
                grab.clear()
            }
        }

        togglebutton("") {
            graphic = ImageView(Image("images/settings.png", true))
            isSelected = false
            setOnAction {
                //Toggle between content & settings
                if (content.isDocked) {
                    content.replaceWith(settings)
                } else {
                    settings.replaceWith(content)
                }
            }
        }
    }
}