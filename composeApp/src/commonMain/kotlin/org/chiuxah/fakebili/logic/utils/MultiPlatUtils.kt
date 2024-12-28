package org.chiuxah.fakebili.logic.utils

import io.ktor.client.engine.HttpClientEngineFactory

expect class MultiPlatUtils() {
    fun showMsg(msg : String)
    fun startUrl(url : String)
    fun createEngine() : HttpClientEngineFactory<*>
}