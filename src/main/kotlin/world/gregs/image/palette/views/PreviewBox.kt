package world.gregs.image.palette.views

import javafx.scene.input.TransferMode
import javafx.scene.layout.Priority
import tornadofx.*
import world.gregs.image.palette.app.Styles.Companion.dragLabel
import world.gregs.image.palette.app.Styles.Companion.previewBox
import world.gregs.image.palette.app.Styles.Companion.previewImage
import world.gregs.image.palette.controllers.Grab

class PreviewBox : View() {

    private val grab: Grab by inject()

    @Suppress("UsePropertyAccessSyntax")
    override val root = stackpane {
        addClass(previewBox)
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS

        progressindicator {
            progress = -1.0
            visibleProperty().bind(grab.preview.loading)
        }

        label("Drag an image here...") {
            addClass(dragLabel)
            borderProperty().bind(grab.preview.border)
            visibleProperty().bind(grab.preview.draggable)
        }

        imageview(null) {
            addClass(previewImage)
            isPreserveRatio = true
            fitWidthProperty().bind(this@stackpane.widthProperty())
            fitHeightProperty().bind(this@stackpane.heightProperty())
            imageProperty().bind(grab.preview.preview)
        }

        //Allow dragging of files
        setOnDragOver {
            if (it.gestureSource != this && it.dragboard.hasFiles()) {
                it.acceptTransferModes(*TransferMode.COPY_OR_MOVE)
            }
            it.consume()
        }

        //Grab palette from images when dropped
        setOnDragDropped {
            if (it.dragboard.hasFiles()) {
                val file = it.dragboard.files.first()
                grab.palette(file)
                it.isDropCompleted = true
            }

            it.consume()
        }
    }
}