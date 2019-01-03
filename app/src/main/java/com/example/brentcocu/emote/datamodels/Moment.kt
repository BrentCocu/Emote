package com.example.brentcocu.emote.datamodels

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
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
}