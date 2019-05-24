package com.qverkk.steganography.encryptors

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

abstract class Encryptor(val filePath: String) {

    val image: BufferedImage = ImageIO.read(File(filePath))
    val width: Int
    val height: Int

    init {
        this.width = image.width
        this.height = image.height
    }

    abstract fun encrypt(text: String)

    fun saveEncryptedData() {
        val file = File(filePath)
        ImageIO.write(image, "png", file)
    }
}