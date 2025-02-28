package com.example.searchlunch.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantList (
    val results : Results = Results()
)

@Serializable
data class Results (
    @SerialName("api_version")
    val apiVersion : String? = "",
    @SerialName("results_available")
    val resultsAvailable : Int? = 0,
    @SerialName("results_returned")
    val resultsReturned : String? = "",
    @SerialName("results_start")
    val resultsStart : Int? = 0,
    val shop : List<Shop?> = emptyList()
)

@Serializable
data class Shop (
    val id: String?,
    val name: String?,
    val address: String?,
    val lat: Double?,
    val lng: Double?,
    val genre: Genre?,
    val catch: String?,
    val access: String?,
    val urls: Urls?,
    val photo: Photo?,
    val open: String?,
    val close: String?,
    @SerialName("other_memo")
    val otherMemo: String?,
    @SerialName("shop_detail_memo")
    val shopDetailMemo: String?,
    @SerialName("budget_memo")
    val budgetMemo: String?,
)

@Serializable
data class Genre (
    val name: String?,
    val catch: String?
)

@Serializable
data class Urls (
    val pc: String?,
)

@Serializable
data class Photo (
    val pc: PcPhoto?
)

@Serializable
data class PcPhoto (
    val l: String?,
    val m: String?,
    val s: String?,
)
