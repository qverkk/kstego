package com.qverkk.steganography.ui

import com.qverkk.steganography.encryptors.Decryptor
import com.qverkk.steganography.encryptors.LowLevelBitEncryption
import javafx.fxml.Initializable
import java.net.URL
import java.util.*
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.FileChooser


class Controller : Initializable {

    @FXML
    private lateinit var encryptChoice: Button

    @FXML
    private lateinit var decryptChoice: Button

    @FXML
    private lateinit var typeChoice: Label

    @FXML
    private lateinit var imageView: ImageView

    @FXML
    private lateinit var imagePathLabel: Label

    @FXML
    private lateinit var selectImageButton: Button

    @FXML
    private lateinit var messageTextArea: TextArea

    @FXML
    private lateinit var secretTextArea: TextArea

    @FXML
    private lateinit var performButton: Button

    private var currentType = "Encrypt"
    private var currentImagePath = ""

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        initListeners()
        changeSettings()
    }

    private fun initListeners() {
        encryptChoice.setOnAction {
            if (currentType == "Decrypt") {
                currentType = "Encrypt"
                changeSettings()
            }
        }

        decryptChoice.setOnAction {
            if (currentType == "Encrypt") {
                currentType = "Decrypt"
                changeSettings()
            }
        }

        selectImageButton.setOnAction {
            val fileChooser = FileChooser()
            val allFilter = FileChooser.ExtensionFilter("All Files (.png, .jpg)", "*.png", "*.jpg")
            val pngFilter = FileChooser.ExtensionFilter("PNG Files (.png)", "*.png")
            val jpgFilter = FileChooser.ExtensionFilter("JPG Files (.jpg)", "*.jpg")
            fileChooser.extensionFilters.addAll(allFilter, pngFilter, jpgFilter)
            val file = fileChooser.showOpenDialog(null)
            if (file != null) {
                currentImagePath = file.path
                imagePathLabel.text = currentImagePath
                imageView.image = Image(file.toURI().toURL().toExternalForm())
            }
        }
    }

    private fun changeSettings() {
        typeChoice.text = currentType
        messageTextArea.isEditable = currentType == "Encrypt"
        imagePathLabel.text = "Image path"
        currentImagePath = ""
        messageTextArea.text = ""
        secretTextArea.text = ""
        if (imageView.image != null) {
            imageView.image = null
        }
        performButton.text = currentType + "ion"
        if (currentType == "Encrypt")
            performButton.text += " And Save"

        performButton.setOnAction {
            if (currentType == "Encrypt") {
                performEncrypt()
            } else {
                performDecrypt()
            }
        }
    }

    private fun performEncrypt() {
        val secret = secretTextArea.text
        val alert = Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK)
        if (currentImagePath.isEmpty()) {
            alert.contentText = "Image isn't selected"
            alert.showAndWait()
            return
        }
        /*if (secret.isNullOrEmpty()) {
            alert.contentText = "Secret key is empty"
            alert.showAndWait()
            return
        }*/
        val message = messageTextArea.text
        if (message.isNullOrEmpty()) {
            alert.contentText = "Message is empty"
            alert.showAndWait()
            return
        }

        val encryptor = LowLevelBitEncryption(currentImagePath)
        encryptor.encrypt(message)
    }

    private fun performDecrypt() {
        val alert = Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK)
        val secret = secretTextArea.text
        if (currentImagePath.isEmpty()) {
            alert.contentText = "Image isn't selected"
            alert.showAndWait()
            return
        }
        /*if (secret.isNullOrEmpty()) {
            alert.contentText = "Secret key is empty"
            alert.showAndWait()
            return
        }*/
        val decryptor = Decryptor(currentImagePath)
        messageTextArea.text = decryptor.decryptLowLevelBits()
    }
}