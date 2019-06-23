package world.gregs.image.palette.app

import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.paint.Color
import javafx.scene.paint.Color.TRANSPARENT
import javafx.scene.paint.Color.rgb
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val previewBox by cssclass()
        val previewImage by cssclass()
        val dragLabel by cssclass()

        val logo by cssclass()
        val menu by cssclass()

        val colourList by cssclass()
        val colourCell by cssclass()
        val colourLabel by cssclass()

        val settings by cssclass()
        val qualitySlider by cssclass()
        val colourSlider by cssclass()


        val textColour: Color  = rgb(137, 137, 137)
        private val lightTextColour: Color  = rgb(241, 243, 242)
        private val backgroundColour: Color = rgb(20, 20, 20)
        private val toolbarBackgroundColour: Color  = rgb(46, 46, 48)
        val buttonHighlightColour: Color  = rgb(207, 70, 60)
        //Scroll bar
        private val trackColour: Color  = rgb(25, 25, 25)
        private val thumbColour: Color  = rgb(204, 204, 204)
    }

    init {

        /*
            Preview area
         */
        previewBox {
            minWidth = 800.px
            minHeight = 450.px
            backgroundColor = multi(backgroundColour)
        }

        previewImage {
            smooth = true
        }

        dragLabel {
            fontSize = 18.px
            textFill = textColour
            padding = box(20.px)
        }

        /*
            Menu
         */
        menu {
            backgroundColor = multi(toolbarBackgroundColour)
            prefHeight = 48.px
            spacing = 0.px
            padding = box(0.px)
        }

        s(toggleButton, button) {
            backgroundColor = multi(TRANSPARENT)
            prefWidth = 48.px
            prefHeight = 48.px
            backgroundRadius = multi(box(0.em))
            and(selected) {
                backgroundColor = multi(buttonHighlightColour)
            }
        }

        logo {
            alignment = Pos.CENTER_LEFT
            padding = box(4.px)
        }

        /*
            Palette
         */
        colourList {
            orientation = Orientation.VERTICAL
            minHeight = 450.px
            backgroundColor = multi(backgroundColour)
            s(listCell) {
                backgroundColor = multi(backgroundColour)
            }
            borderColor = multi(box(toolbarBackgroundColour))
            borderWidth = multi(box(0.px, 0.px, 0.px, 1.px))
            cursor = Cursor.HAND
        }

        colourCell {
            prefWidth = 135.px
            and(focused) {
                backgroundInsets = multi(box(0.px))
                backgroundRadius = multi(box(0.px))
            }
        }

        colourLabel {
            prefWidth = 100.px
            prefHeight = 100.px
            padding = box(0.px, 0.px, 0.px, 8.px)
            alignment = Pos.BOTTOM_LEFT
            textAlignment = TextAlignment.LEFT
            fontSize = 14.px
            fontWeight = FontWeight.BOLD
        }

        /*
            Scroll bars
         */
        scrollBar {
            and(horizontal, vertical) {
                s(track) {
                    backgroundColor = multi(trackColour)
                    borderColor = multi(box(trackColour))
                    backgroundRadius = multi(box(0.em))
                    borderRadius = multi(box(0.em))
                }
            }

            and(horizontal) {
                s(incrementButton, decrementButton) {
                    borderColor = multi(box(TRANSPARENT))
                    backgroundRadius = multi(box(0.em))
                    padding = box(0.px, 0.px, 10.px, 0.px)
                }
            }

            and(vertical) {
                s(incrementButton, decrementButton) {
                    backgroundRadius = multi(box(0.em))
                    padding = box(0.px, 10.px, 0.px, 0.px)
                }
            }

            s(incrementArrow, decrementArrow) {
                shape = " "
                padding = box(.015.em, 0.em)
            }

            and(vertical) {
                s(incrementArrow, decrementArrow) {
                    shape = " "
                    padding = box(0.em, .015.em)
                }
            }

            and(horizontal, vertical) {
                s(thumb) {
                    backgroundColor = multi(thumbColour)
                    backgroundInsets = multi(box(0.px, 0.px, 0.px, 0.px))
                    backgroundRadius = multi(box(0.em))

                    /*and(hover) {
                        backgroundColor = multi(thumbColour)
                        backgroundInsets = multi(box(2.px, 0.px, 0.px, 0.px))
                        backgroundRadius = multi(box(2.em))
                    }*/
                }
            }
        }

        /*
            Settings
         */
        settings {
            backgroundColor = multi(backgroundColour)
        }

        fieldset {
            padding = box(20.px)
            s(legend) {
                textFill = lightTextColour
                fontSize = 16.px
            }
        }

        field {
            s(label) {
                textFill = textColour
                fontSize = 14.px
            }
        }

        s(slider, axis) {
            tickLabelFill = textColour
        }

        slider {
            showTickMarks = true
            showTickLabels = true
            snapToTicks = true

            and(qualitySlider) {
                majorTickUnit = 4.0
                minorTickCount = 3
            }

            and(colourSlider) {
                majorTickUnit = 49.0
                minorTickCount = 48
            }
        }
    }
}