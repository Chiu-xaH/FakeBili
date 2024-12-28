package org.chiuxah.fakebili.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.chiuxah.fakebili.logic.network.api.ApiResult
import org.chiuxah.fakebili.logic.network.bean.BiliTicketResponse
import org.chiuxah.fakebili.logic.network.bean.SearchBean
import org.chiuxah.fakebili.logic.network.bean.SearchResponse
import org.chiuxah.fakebili.logic.network.servicecreator.BiliServiceCreator

//ViewModel
class NetworkViewModel : ViewModel() {
    private val biliApi = BiliServiceCreator.apiService

    var ticket = ""

    private val _ticketState = MutableStateFlow<ApiResult<BiliTicketResponse>>(ApiResult.Error(Throwable("Idle")))
    val ticketState: StateFlow<ApiResult<BiliTicketResponse>> = _ticketState

    fun fetchBiliTicket() {
        viewModelScope.launch {
            _ticketState.value = biliApi.getBiliTicket()
        }
    }

    var videoSearchList = emptyList<SearchBean>()

    private val _searchState = MutableStateFlow<ApiResult<SearchResponse>>(ApiResult.Error(Throwable("Idle")))
    val searchState: StateFlow<ApiResult<SearchResponse>> = _searchState

    fun searchVideos(query: String,page: Int = 1,pageSize: Int = 42) {
        viewModelScope.launch {
            _searchState.value = biliApi.searchVideos(query,ticket,page,pageSize)
        }
    }
}