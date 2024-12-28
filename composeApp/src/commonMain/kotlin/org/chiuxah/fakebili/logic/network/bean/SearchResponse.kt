package org.chiuxah.fakebili.logic.network.bean

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SearchResponse(
    val data : SearchResult
)
@Serializable
data class SearchResult(
    val result : List<SearchResultItem>
)
@Serializable
data class SearchResultItem(
    @SerialName("result_type")
    val type : String,
    val data : List<SearchBean>
)
@Serializable
data class SearchBean(
    val tag : String?,
    val duration : String?,
    @SerialName("pic")
    val imgUrl : String?,
    @SerialName("upic")
    val authorImg : String?,
    var title : String,
    val bvid : String,
    val arcurl : String,
    val author : String,
)

object searchType {
    //    val tips = "tips"
//    val brand_ad = "brand_ad"
    val video = "video"
//    val esports = "esports"
    //略
}