package io.android.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "amiiboSeries")
    val amiiboSeries: String,
    @ColumnInfo(name = "character")
    val character: String,
    @ColumnInfo(name = "gameSeries")
    val gameSeries: String,
    @ColumnInfo(name = "head")
    val head: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "tail")
    val tail: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)