package org.chiuxah.fakebili.logic.network.bean

import kotlinx.serialization.Serializable


//JSON解析数据类
@Serializable
data class BiliTicketResponse(
    val data : BiliTicketBean
)
@Serializable
data class BiliTicketBean(
    val ticket : String
)
