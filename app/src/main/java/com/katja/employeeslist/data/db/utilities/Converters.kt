package com.katja.employeeslist.data.db.utilities

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun stringToCalendar(value: String): Calendar {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val date = formatter.parse(value)
        return Calendar.getInstance().apply { time = date }
    }

    @TypeConverter
    fun calendarToString(calendar: Calendar): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        return formatter.format(calendar.time)
    }

}