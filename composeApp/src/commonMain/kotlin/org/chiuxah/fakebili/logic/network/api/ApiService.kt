package org.chiuxah.fakebili.logic.network.api

import org.chiuxah.fakebili.logic.network.bean.BiliTicketResponse
import org.chiuxah.fakebili.logic.network.bean.SearchResponse

//接口
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Throwable) : ApiResult<Nothing>()
}

interface ApiService {
    //得到cookie
    suspend fun getBiliTicket() : ApiResult<BiliTicketResponse>
    //搜索视频
    suspend fun searchVideos(
        query: String,
        ticket: String,
        page: Int = 1,
        pageSize: Int = 42
    ) : ApiResult<SearchResponse>
}