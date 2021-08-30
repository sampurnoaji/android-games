package io.android.core.util.mapper

import io.android.core.data.source.local.entity.GameEntity
import io.android.core.data.source.remote.response.GameListResponse
import io.android.core.domain.model.Game

object GameMapper {
    fun toEntities(response: GameListResponse): List<GameEntity> {
        return response.amiibo?.map {
            GameEntity(
                amiiboSeries = it?.amiiboSeries.orEmpty(),
                character = it?.character.orEmpty(),
                gameSeries = it?.gameSeries.orEmpty(),
                head = it?.head.orEmpty(),
                image = it?.image.orEmpty(),
                name = it?.name.orEmpty(),
                tail = it?.tail.orEmpty(),
                type = it?.type.orEmpty(),
            )
        } ?: emptyList()
    }

    fun toEntity(game: Game): GameEntity {
        return GameEntity(
            id = game.id,
            amiiboSeries = game.amiiboSeries,
            character = game.character,
            gameSeries = game.gameSeries,
            head = game.head,
            image = game.image,
            name = game.name,
            tail = game.tail,
            type = game.type,
        )
    }

    fun toDomain(games: List<GameEntity>): List<Game> {
        return games.map {
            Game(
                id = it.id,
                amiiboSeries = it.amiiboSeries,
                character = it.character,
                gameSeries = it.gameSeries,
                head = it.head,
                image = it.image,
                name = it.name,
                tail = it.tail,
                type = it.type,
            )
        }
    }
}