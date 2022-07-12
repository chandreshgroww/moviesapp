package com.example.moviesapp.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Convertors {

    @TypeConverter
    fun saveIntList(data: List<Int>): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun getIntList(data: String): List<Int> {
        return Gson().fromJson(data, object : TypeToken<List<Int>>() {}.type)
    }

}
