package com.example.androidtestapikotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Parcelize
data class Color(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable {
    enum class KEYS(val value: String) {
        ALBUM_ID("albumId"),
        ID("id"),
        TITLE("title"),
        URL("url"),
        THUMBNAIL_URL("thumbnailUrl")
    }
}

@Parcelize
data class DatabasePhoto(
    val id: String,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable


fun List<Color>.asLocalModel(): List<DatabasePhoto> {
    return map {
        DatabasePhoto(
            it.id.toString(),
            it.title,
            it.url,
            it.thumbnailUrl
        )
    }
}

