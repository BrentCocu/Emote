package com.example.brentcocu.emote.datamodels

import android.text.format.DateFormat
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Emotion::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("emotionId"),
        onDelete = CASCADE
    )],
    indices = [Index(
        value = ["emotionId"]
    )]
)
class Moment(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var emotionId: Int,
    var date: Date,
    var description: String,
    var people: List<String>,
    var sensations: String
) {
    constructor(
        emotionId: Int,
        date: Date,
        description: String,
        people: List<String>,
        sensations: String
    ) : this(null, emotionId, date, description, people, sensations)

    constructor() : this(null, -1, Date(), "", listOf(""), "")

    fun dateToString(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(date)
    }

    fun timeToString(): String {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(date)
    }

    fun dateTimeToString(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return formatter.format(date)
    }
}