package com.example.rawggames.data.local

import androidx.room.TypeConverter
import com.example.rawggames.data.local.entity.GenreEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreEntityListConverter {
    @TypeConverter
    fun fromString(value: String?): List<GenreEntity>? {
        if (value == null) {
            return null
        }

        val listType = object : TypeToken<List<GenreEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(value: List<GenreEntity>?): String? {
        if (value == null) {
            return null
        }

        return Gson().toJson(value)
    }
}