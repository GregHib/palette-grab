package world.gregs.image.palette.views

import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.*
import javafx.util.Duration
import tornadofx.*
import world.gregs.image.palette.app.Styles.Companion.colourCell
import world.gregs.image.palette.app.Styles.Companion.colourLabel
import world.gregs.image.palette.app.Styles.Companion.colourList
import world.gregs.image.palette.controllers.Grab

class Palette : View() {

    private val grab: Grab by inject()

    override val root = listview(grab.colours.list) {
        addClass(colourList)

        //Copy colour to clipboard if clicked
        selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            if (newValue == null) {
                return@addListener
            }

            val clipboard = Clipboard.getSystemClipboard()
            val hex = newValue.colour.toHex()
            //Ignore if already copied
            if (clipboard.hasString()) {
                if (clipboard.string == hex) {
                    return@addListener
                }
            }

            val content = ClipboardContent()
            if (content.putString(hex)) {
                clipboard.setContent(content)
                println("Copied colour to clipboard: '$hex'")
                //Animate
                if (!grab.settings.skipAnimation()) {
                    newValue.copied = true
                }
            }
        }

        //Can't cache as need to bind for animation
        cellFormat { cell ->
            addClass(colourCell)
            graphic = stackpane {
                label(cell.colour.toHex()) {
                    addClass(colourLabel)
                    background = Background(BackgroundFill(cell.colour.rgb, CornerRadii(10.0), insets))
                    textFill = cell.colour.contrast
                    border = Border(
                        BorderStroke(
                            cell.colour.contrast.deriveColor(1.0, 1.0, 1.0, 0.25),
                            BorderStrokeStyle.SOLID,
                            CornerRadii(10.0),
                            BorderWidths.DEFAULT,
                            insets
                        )
                    )

                    //Animate copy icon appearing then disappearing
                    if(cell.copied) {
                        children.add(
                            imageview("images/copied.png") {
                                opacity = 0.0
                                val timeline = Timeline(
                                    KeyFrame(Duration.seconds(0.0), KeyValue(opacityProperty(), 0)),
                                    KeyFrame(Duration.seconds(0.05), KeyValue(opacityProperty(), 1)),
                                    KeyFrame(Duration.seconds(0.25), KeyValue(opacityProperty(), 1)),
                                    KeyFrame(Duration.seconds(0.3), KeyValue(opacityProperty(), 0))
                                )
                                timeline.play()
                            }
                        )
                        cell.copied = false
                    }
                }
            }
        }
    }

}