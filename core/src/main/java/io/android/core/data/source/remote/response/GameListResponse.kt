package io.android.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GameListResponse(
    @field:SerializedName("amiibo")
    val amiibo: List<Amiibo?>? = null
) {
    data class Amiibo(
        @field:SerializedName("amiiboSeries")
        val amiiboSeries: String? = null,
        @field:SerializedName("character")
        val character: String? = null,
        @field:SerializedName("gameSeries")
        val gameSeries: String? = null,
        @field:SerializedName("head")
        val head: String? = null,
        @field:SerializedName("image")
        val image: String? = null,
        @field:SerializedName("name")
        val name: String? = null,
        @field:SerializedName("tail")
        val tail: String? = null,
        @field:SerializedName("type")
        val type: String? = null
    )
}