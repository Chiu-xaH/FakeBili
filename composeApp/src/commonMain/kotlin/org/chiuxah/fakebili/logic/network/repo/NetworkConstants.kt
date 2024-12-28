package org.chiuxah.fakebili.logic.network.repo

import kotlinx.serialization.json.Json

//放一些常量
object NetworkConstants {
    val pcUA = "Mozilla/5.0 (X11; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/115.0"
    val GET_BILI_TICKET_HOST = "api.bilibili.com"
    inline fun <reified T> parseJson(jsonStr : String) : T {
        val json = Json { ignoreUnknownKeys = true } //忽略未定义字段
        return json.decodeFromString<T>(jsonStr)
    }
}