package com.example.jikan.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
data class AnimeDetailData(
    @SerializedName("data")
    var data: AnimeDetail
) : Parcelable

@Parcelize
data class AnimeDetail(
    @SerializedName("mal_id")
    val id: Int,

    @SerializedName("url")
    val url: String? = "",
    @SerializedName("synopsis")
    val synopsis: String? = "",

    @SerializedName("trailer")
    var trailer: Trailer? = null,

    @SerializedName("genres")
    val genres: List<Genre>? = null


    ):android.os.Parcelable


@Parcelize
data class Trailer(
    @SerializedName("youtube_id")
    val youtubeId: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("embed_url")
    val embedUrl: String? = null,

    @SerializedName("images")
    val images: TrailerImages? = null
) : android.os.Parcelable

@Serializable
@Parcelize
data class TrailerImages(
    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("small_image_url")
    val smallImageUrl: String? = null,

    @SerializedName("medium_image_url")
    val mediumImageUrl: String? = null,

    @SerializedName("large_image_url")
    val largeImageUrl: String? = null,

    @SerializedName("maximum_image_url")
    val maximumImageUrl: String? = null
) : android.os.Parcelable


@Serializable
@Parcelize
data class Genre(
    @SerializedName("mal_id")
    val malId: Int,

    @SerializedName("type")
    val type: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
) : android.os.Parcelable