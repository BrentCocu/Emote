package com.example.brentcocu.emote.datamodels

import android.graphics.Color
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
data class Emotion(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var name: String,
    var color: Int
) {
    constructor(name: String, color: Int): this(null, name, color)
    constructor(): this(null, "", Color.parseColor("#1976d2"))
}