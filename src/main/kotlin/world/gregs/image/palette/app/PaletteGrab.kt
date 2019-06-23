package world.gregs.image.palette.app

import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.App
import tornadofx.launch
import world.gregs.image.palette.views.MainView

class PaletteGrab : App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.icons.add(Image("images/icon.png"))
    }
}

fun main(args: Array<String>) {
    launch<PaletteGrab>(*args)
}