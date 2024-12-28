package org.chiuxah.fakebili.logic.utils

import android.content.Intent
import android.net.Uri
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.android.Android
import org.chiuxah.fakebili.MyApplication
import org.chiuxah.fakebili.ui.uitls.myToast

actual class MultiPlatUtils  {
    actual fun showMsg(msg : String) {
        myToast(msg)
    }

    actual fun startUrl(url: String) {
        try {
            val it = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            MyApplication.context.startActivity(it)
        } catch (e : Exception) {
            myToast("启动浏览器失败")
        }
    }
    actual fun createEngine() : HttpClientEngineFactory<*> {
        return Android
    }
}