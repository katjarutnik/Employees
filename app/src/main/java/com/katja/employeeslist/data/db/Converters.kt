package com.katja.employeeslist.data.db

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun doubleToString(value: Double): String = value.toString()

    @TypeConverter
    fun stringToDouble(value: String): Double = value.toDouble()
}