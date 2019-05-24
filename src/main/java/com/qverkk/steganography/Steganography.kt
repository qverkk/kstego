package com.qverkk.steganography

import com.qverkk.steganography.ui.Controller
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class Steganography : Application() {
    override fun start(primaryStage: Stage?) {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/MainUi.fxml"))
        fxmlLoader.setController(Controller())
        primaryStage?.scene = Scene(fxmlLoader.load())
        primaryStage?.show()

    }
}

fun main(args: Array<String>) {
    Application.launch(Steganography().javaClass, *args)
}