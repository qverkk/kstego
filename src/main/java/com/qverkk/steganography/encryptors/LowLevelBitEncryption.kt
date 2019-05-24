package com.qverkk.steganography.encryptors

import java.awt.Color

class LowLevelBitEncryption(filePath: String) : Encryptor(filePath) {

    var row = 0
    var col = 0

    override fun encrypt(text: String) {
        for (c in text.toCharArray()) {
            encryptByte(c)
        }

        col = 0
        row = 0

        saveEncryptedData()
    }

    fun encryptByte(character: Char) {
        for (i in 0 until 8) {
            encryptBitCharacter((character.toInt() shr i) and 0b1)
        }
    }

    private fun encryptBitCharacter(c: Int) {
        if (col < image.width - 1) {
            val mask = 255
            val rgb = image.getRGB(col, row)
            val r = (rgb shr 16) and mask
            val g = (rgb shr 8) and mask

            var b = image.getRGB(col + 1, row) and mask
            b -= c

            val color = Color(r, g, b)
            image.setRGB(col, row, color.rgb)

            col += 2
        } else {
            row++
            col = 0
        }
    }
}