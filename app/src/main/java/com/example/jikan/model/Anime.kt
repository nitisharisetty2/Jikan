package com.example.jikan.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Anime(
    @SerializedName("data")
    var data: List<AnimeData>
) : Parcelable


@Parcelize
data class AnimeData(
    @SerializedName("mal_id")
    val id: Int,

    @SerializedName("title_english")
    val title: String? = "",

    @SerializedName("episodes")
    val numberOfEpisodes: Int? = null,

    @SerializedName("score")
    val rating: Double? = 0.0,

    @SerializedName("images")
    val images: Images? = null
): Parcelable


@Parcelize
data class Images(
    @SerializedName("jpg")
    val jpg: Jpg? = null,
    ) : Parcelable


@Parcelize
data class Jpg(
    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("small_image_url")
    val smallImageUrl: String? = null,

    @SerializedName("large_image_url")
    val largeImageUrl: String? = null
) : Parcelable