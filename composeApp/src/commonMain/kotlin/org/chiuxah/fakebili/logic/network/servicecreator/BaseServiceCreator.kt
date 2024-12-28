package org.chiuxah.fakebili.logic.network.servicecreator

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import org.chiuxah.fakebili.logic.network.repo.NetworkConstants
import org.chiuxah.fakebili.logic.utils.MultiPlatUtils


//基类
open class BaseServiceCreator(
    private val hosts: String,
    private val clientBuilder: HttpClient.() -> Unit
) {
    protected val client: HttpClient by lazy {
        HttpClient(MultiPlatUtils().createEngine()) {
            install(ContentNegotiation) { json() }
//            install(Logging) {
//                level = LogLevel.ALL
//                logger = Logger.DEFAULT
//            }
            defaultRequest {
                header(HttpHeaders.UserAgent, NetworkConstants.pcUA)
                url {
                    protocol = URLProtocol.HTTPS
                    host = hosts
                }
            }
            clientBuilder
        }
    }
}