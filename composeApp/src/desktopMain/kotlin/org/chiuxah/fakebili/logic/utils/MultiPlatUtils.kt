package org.chiuxah.fakebili.logic.utils

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO
import java.awt.Desktop
import java.net.URI
import javax.swing.JOptionPane

actual class MultiPlatUtils actual constructor() {
    actual fun showMsg(msg : String) {
        JOptionPane.showMessageDialog(
            null,
            msg
        )
    }

    actual fun startUrl(url: String) {
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(URI(url))
            }
        }
    }

    actual fun createEngine() : HttpClientEngineFactory<*> {
        return CIO
    }
}