package com.example.moviesapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

data class GenreResult(
    val genres: List<SortFilter>
)

@Parcelize
@Entity(tableName = "all_genre")
data class SortFilter(
    @PrimaryKey
    var id: Int,
    var name: String,
    var idString: String = id.toString(),
    var isSelected: Boolean = false
): Parcelable