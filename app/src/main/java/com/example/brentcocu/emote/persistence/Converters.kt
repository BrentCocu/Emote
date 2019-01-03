package com.example.brentcocu.emote.persistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(value: Date): Long {
        return value.time
    }

    @TypeConverter
    fun listToJson(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String> {
        val objects = Gson().fromJson(value, Array<String>::class.java)
        return objects.toList()
    }
}