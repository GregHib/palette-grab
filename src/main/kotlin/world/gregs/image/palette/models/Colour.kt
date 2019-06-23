package world.gregs.image.palette.models

/**
 * Colour wrapper for [copied] to toggle copy animation
 */
data class Colour(val colour: HSLColour) {
    var copied = false
}