package com.example.moviez.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class TypeConvertorClass {
//    @TypeConverter
//    fun toMeaningJson(meaning: List<Meaning>) : String {
//        return jsonParser.toJson(
//            meaning,
//            object : TypeToken<ArrayList<Meaning>>(){}.type
//        ) ?: "[]"
//    }
//
//    @TypeConverter
//    fun fromMeaningsJson(json: String): List<Meaning>{
//        return jsonParser.fromJson<ArrayList<Meaning>>(
//            json,
//            object: TypeToken<ArrayList<Meaning>>(){}.type
//        ) ?: emptyList()
//    }
}