package io.android.core.domain.model

import java.io.Serializable

data class Game(
    val id: Int,
    val amiiboSeries: String,
    val character: String,
    val gameSeries: String,
    val head: String,
    val image: String,
    val name: String,
    val tail: String,
    val type: String,
    val isFavorite: Boolean
): Serializable