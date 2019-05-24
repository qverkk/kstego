package com.qverkk.steganography.encryptors

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Decryptor(val filePath: String) {

    private var row = 0
    private var col = 0
    private var originalImage: BufferedImage = ImageIO.read(File(filePath))
    private var tempBitVal = 0


    fun decryptLowLevelBits(): String {
        val stringBuilder = StringBuilder()

        while (col < originalImage.width - 1 && row < originalImage.height) {
            var c: Char = 0.toChar()

            for (i in 0..7) {
                decryptBitCharacter()

                c = (c.toInt() shl 1).toChar()
                c = (c.toInt() or tempBitVal).toChar()
            }

            c = (Integer.reverse(c.toInt() shl 24) and 255).toChar()

            stringBuilder.append(c)
        }

        return stringBuilder.toString()
    }

    private fun decryptBitCharacter() {
        if (col < originalImage.width.minus(1)) {
            val mask = 255
            val rgb = originalImage.getRGB(col, row)

            val b = rgb and mask

            val newB = originalImage.getRGB(col + 1, row) and mask

            tempBitVal = Math.abs(newB - b)

            col += 2
        } else {
            row++
            col = 0
        }
    }
}