package world.gregs.image.palette.views

import javafx.util.StringConverter
import tornadofx.*
import world.gregs.image.palette.app.Styles.Companion.colourSlider
import world.gregs.image.palette.app.Styles.Companion.qualitySlider
import world.gregs.image.palette.app.Styles.Companion.settings
import world.gregs.image.palette.controllers.Grab

/**
 * Settings view
 */
class Settings : View() {

    private val grab: Grab by inject()

    override val root = form {
        addClass(settings)
        fieldset("Settings") {
            hbox(20) {
                vbox {
                    field("Colour quality") {
                        tooltip("Palette calculation accuracy at the cost of processing speed.")
                        slider(IntRange(1, 9)) {
                            addClass(qualitySlider)
                            valueProperty().bindBidirectional(grab.settings.quality)
                            isShowTickMarks = true
                            isShowTickLabels = true
                            labelFormatter = object : StringConverter<Double>() {
                                override fun toString(n: Double): String? = when (n) {
                                    1.0 -> "High"
                                    9.0 -> "Low"
                                    else -> null
                                }

                                override fun fromString(string: String): Double? {
                                    return when (string) {
                                        "High" -> 0.0
                                        "Low" -> 9.0
                                        else -> null
                                    }
                                }
                            }
                            //Save preference and reload palette once user has finished changing setting
                            valueProperty().addListener { _, oldValue, newValue ->
                                if (oldValue != newValue && newValue is Double && newValue % 1.0 == 0.0) {
                                    grab.reload()
                                    grab.settings.save()
                                }
                            }
                        }
                    }

                    field("Palette colour count") {
                        tooltip("The number of colours to grab from the image.")
                        slider(IntRange(1, 100)) {
                            addClass(colourSlider)
                            isShowTickMarks = true
                            isShowTickLabels = true
                            valueProperty().bindBidirectional(grab.settings.colourCount)
                            //Save preference and reload palette once user has finished changing setting
                            valueProperty().addListener { _, oldValue, newValue ->
                                if (oldValue != newValue && newValue is Double && newValue % 1.0 == 0.0) {
                                    grab.reload()
                                    grab.settings.save()
                                }
                            }
                        }
                    }

                    field("Ignore white from palette") {
                        tooltip("Ignores white hues from being shown")
                        checkbox {
                            selectedProperty().bindBidirectional(grab.settings.ignoreWhite)
                            //Save preference and reload palette once user has finished changing setting
                            selectedProperty().addListener { _, oldValue, newValue ->
                                if (oldValue != newValue) {
                                    grab.reload()
                                    grab.settings.save()
                                }
                            }
                        }
                    }

                    field("Skip copy animation") {
                        tooltip("Skips the colour copy animation.")
                        checkbox {
                            selectedProperty().bindBidirectional(grab.settings.skipAnimation)
                            //Save preference
                            selectedProperty().addListener { _, oldValue, newValue ->
                                if(oldValue != newValue) {
                                    grab.settings.save()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}