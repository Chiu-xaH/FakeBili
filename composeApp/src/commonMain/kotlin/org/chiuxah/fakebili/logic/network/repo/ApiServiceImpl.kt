package org.chiuxah.fakebili.logic.network.repo

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.runBlocking
import org.chiuxah.fakebili.logic.utils.Encrypt.hmacSha256
import org.chiuxah.fakebili.logic.network.api.ApiResult
import org.chiuxah.fakebili.logic.network.api.ApiService
import org.chiuxah.fakebili.logic.network.bean.BiliTicketResponse
import org.chiuxah.fakebili.logic.network.bean.SearchResponse
import org.chiuxah.fakebili.logic.network.repo.NetworkConstants.parseJson
import java.time.Instant

//接口实现
class ApiServiceImpl(private val client : HttpClient) : ApiService {
    override suspend fun getBiliTicket(): ApiResult<BiliTicketResponse> = runBlocking {
        try {
            val ts: Long = Instant.now().epochSecond
            val hexSign: String = hmacSha256("XgwSnGZ1p", "ts$ts")
            val url = "bapis/bilibili.api.ticket.v1.Ticket/GenWebTicket"

            val response  = client.post(url) {
                parameter("key_id", "ec02")
                parameter("hexsign", hexSign)
                parameter("context[ts]", ts)
                parameter("csrf","")
            }.bodyAsText()
            val json = parseJson<BiliTicketResponse>(response)
            ApiResult.Success(json)
        } catch (e:Exception) {
            ApiResult.Error(e)
        }
    }

    override suspend fun searchVideos(
        query: String,
        ticket: String,
        page : Int,
        pageSize : Int,
    ): ApiResult<SearchResponse>  = runBlocking {
        try {
            val url = "x/web-interface/wbi/search/all/v2"

            val response  = client.get(url) {
                parameter("page",page)
                parameter("page_size",pageSize)
                parameter("keyword",query)
                header(HttpHeaders.Referrer,"https://search.bilibili.com/")
                header(HttpHeaders.Cookie,"bili_ticket=$ticket")
            }.bodyAsText()
            val json = parseJson<SearchResponse>(response)
            ApiResult.Success(json)
        } catch (e:Exception) {
            ApiResult.Error(e)
        }
    }
}
