package org.chiuxah.fakebili

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.chiuxah.fakebili.ui.main.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "FakeBili",
    ) {
        App()
    }
}