package world.gregs.image.palette.models

import javafx.scene.paint.Color

/**
 * The HSLColour class provides methods to manipulate HSL (Hue, Saturation
 * Luminance) values to create a corresponding Colour object using the RGB
 * ColourSpace.
 *
 *
 * The HUE is the colour, the Saturation is the purity of the colour (with
 * respect to grey) and Luminance is the brightness of the colour (with respect
 * to black and white)
 *
 *
 * The Hue is specified as an angel between 0 - 360 degrees where red is 0,
 * green is 120 and blue is 240. In between you have the colours of the rainbow.
 * Saturation is specified as a percentage between 0 - 100 where 100 is fully
 * saturated and 0 approaches gray. Luminance is specified as a percentage
 * between 0 - 100 where 0 is black and 100 is white.
 *
 *
 * In particular the HSL colour space makes it easier change the Tone or Shade
 * of a colour by adjusting the luminance value.
 * @param rgb the RGB Colour object
 */
data class HSLColour(val rgb: Color) {

    /**
     * Get the HSL values.
     * @return the HSL values.
     */
    val hsl: DoubleArray = fromRGB(rgb)

    /**
     * Get the Alpha value.
     * @return the Alpha value.
     */
    val alpha = rgb.opacity / 255.0

    /**
     * Get the Hue value.
     * @return the Hue value.
     */
    val hue: Double
        get() = hsl[0]

    /**
     * Get the Luminance value.
     * @return the Luminance value.
     */
    val luminance: Double
        get() = hsl[2]

    /**
     * Get the Saturation value.
     * @return the Saturation value.
     */
    val saturation: Double
        get() = hsl[1]

    /**
     * Create a RGB Colour object that is the complementary colour of this
     * HSLColour. This is a convenience method. The complementary colour is
     * determined by adding 180 degrees to the Hue value.
     * @return the RGB Colour object
     */
    val complementary: Color
        get() {
            val hue = (hsl[0] + 180.0) % 360.0
            return toRGB(hue, hsl[1], hsl[2])
        }

    /**
     * Create a RGB Colour object that is the opposite colour of this
     * HSLColour. This is a convenience method. The complementary colour is
     * determined by modifying luminance.
     * @return the RGB Colour object
     */
    val contrast: Color =
        toRGB(hsl[0], hsl[1], if (luminance < 50) 70.0 else 30.0)

    /**
     * Get the hex value of this colour
     * @return hexadecimal string
     */
    fun toHex(): String {
        return String.format(
            "#%02X%02X%02X",
            (rgb.red * 255).toInt(),
            (rgb.green * 255).toInt(),
            (rgb.blue * 255).toInt()
        ).toLowerCase()
    }

    override fun toString(): String {
        return "HSLColour[h=${hsl[0]},s=${hsl[1]},l=${hsl[2]},alpha=$alpha]"
    }

    companion object {

        /**
         * Convert a RGB Colour to it corresponding HSL values.
         * @return an array containing the 3 HSL values.
         */
        fun fromRGB(colour: Color): DoubleArray {
            //Minimum and Maximum RGB values are used in the HSL calculations
            val min = Math.min(colour.red, Math.min(colour.green, colour.blue))
            val max = Math.max(colour.red, Math.max(colour.green, colour.blue))

            //Calculate the Hue
            var h = 0.0

            when (max) {
                min -> h = 0.0
                colour.red -> h = (60 * (colour.green - colour.blue) / (max - min) + 360) % 360
                colour.green -> h = 60 * (colour.blue - colour.red) / (max - min) + 120
                colour.blue -> h = 60 * (colour.red - colour.green) / (max - min) + 240
            }

            //Calculate the Luminance
            val l = (max + min) / 2

            //Calculate the Saturation
            val s: Double

            s = when {
                max == min -> 0.0
                l <= 0.5 -> (max - min) / (max + min)
                else -> (max - min) / (2.0 - max - min)
            }

            return doubleArrayOf(h, s * 100, l * 100)
        }

        /**
         * Convert HSL values to a RGB Colour.
         * H (Hue) is specified as degrees in the range 0 - 360.
         * S (Saturation) is specified as a percentage in the range 1 - 100.
         * L (Lumanance) is specified as a percentage in the range 1 - 100.
         *
         * @param hsl   an array containing the 3 HSL values
         * @param alpha the alpha value between 0 - 1
         * @returns the RGB Colour object
         */
        @JvmOverloads
        fun toRGB(hsl: DoubleArray, alpha: Double = 1.0): Color {
            return toRGB(hsl[0], hsl[1], hsl[2], alpha)
        }

        /**
         * Convert HSL values to a RGB Colour.
         *
         * @param h     Hue is specified as degrees in the range 0 - 360.
         * @param s     Saturation is specified as a percentage in the range 1 - 100.
         * @param l     Lumanance is specified as a percentage in the range 1 - 100.
         * @param alpha the alpha value between 0 - 1
         * @returns the RGB Colour object
         */
        @JvmOverloads
        fun toRGB(h: Double, s: Double, l: Double, alpha: Double = 1.0): Color {
            var h = h
            var s = s
            var l = l
            if (s < 0.0 || s > 100.0) {
                val message = "Colour parameter outside of expected range - Saturation"
                throw IllegalArgumentException(message)
            }

            if (l < 0.0 || l > 100.0) {
                val message = "Colour parameter outside of expected range - Luminance"
                throw IllegalArgumentException(message)
            }

            if (alpha < 0.0 || alpha > 1.0) {
                val message = "Colour parameter outside of expected range - Alpha"
                throw IllegalArgumentException(message)
            }

            //  Formula needs all values between 0 - 1.

            h %= 360.0
            h /= 360.0
            s /= 100.0
            l /= 100.0

            val q: Double

            q = if (l < 0.5)
                l * (1 + s)
            else
                l + s - s * l

            val p = 2 * l - q

            var r = Math.max(0.0, HueToRGB(p, q, h + 1.0 / 3.0))
            var g = Math.max(0.0, HueToRGB(p, q, h))
            var b = Math.max(0.0, HueToRGB(p, q, h - 1.0 / 3.0))

            r = Math.min(r, 1.0)
            g = Math.min(g, 1.0)
            b = Math.min(b, 1.0)

            return Color(r, g, b, alpha)
        }

        private fun HueToRGB(p: Double, q: Double, h: Double): Double {
            var h = h
            if (h < 0) h += 1.0

            if (h > 1) h -= 1.0

            if (6 * h < 1) {
                return p + (q - p) * 6.0 * h
            }

            if (2 * h < 1) {
                return q
            }

            return if (3 * h < 2) {
                p + (q - p) * 6.0 * (2.0 / 3.0 - h)
            } else p

        }
    }
}