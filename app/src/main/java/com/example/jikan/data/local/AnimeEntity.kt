package com.example.jikan.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jikan.model.AnimeData
import com.example.jikan.model.AnimeDetail
import com.example.jikan.model.Genre
import com.example.jikan.model.Images
import com.example.jikan.model.Jpg

@Entity(tableName = "anime")
data class AnimeEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    val title: String,
    val episodes: Int,
    val rating: Double,
    val imageUrl: String,

    val synopsis: String? = null,
    val genres: String? = null
)

fun AnimeData.toEntity(): AnimeEntity {
    return AnimeEntity(
        id = id,
        title = title ?: "",
        episodes = numberOfEpisodes ?: 0,
        rating = rating ?: 0.0,
        imageUrl = images?.jpg?.smallImageUrl ?: ""
    )
}

fun AnimeEntity.toUiModel(): AnimeData {
    return AnimeData(
        id = id,
        title = title,
        numberOfEpisodes = episodes,
        rating = rating,
        images = Images(
            jpg = Jpg(
                smallImageUrl = imageUrl
            )
        )
    )
}

fun AnimeEntity.updateWithDetail(
    synopsis: String?,
    genres: List<Genre>?
): AnimeEntity {
    return copy(
        synopsis = synopsis,
        genres = genres?.joinToString(", ") { it.name }
    )
}

fun AnimeEntity.toAnimeDetail(): AnimeDetail {
    return AnimeDetail(
        id = id,
        synopsis = synopsis,
        genres = genres
            ?.split(",")
            ?.map { name ->
                Genre(
                    malId = 0,
                    type = "",
                    name = name.trim(),
                    url = ""
                )
            }
    )
}
