package org.chiuxah.fakebili.logic.network.servicecreator

import org.chiuxah.fakebili.logic.network.api.ApiService
import org.chiuxah.fakebili.logic.network.repo.ApiServiceImpl
import org.chiuxah.fakebili.logic.network.repo.NetworkConstants

//子类
object BiliServiceCreator : BaseServiceCreator(NetworkConstants.GET_BILI_TICKET_HOST,{}) {
    val apiService : ApiService by lazy { ApiServiceImpl(client) }
}