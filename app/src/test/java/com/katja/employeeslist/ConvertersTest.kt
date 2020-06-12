package com.katja.employeeslist

import com.katja.employeeslist.data.db.utilities.Converters
import org.junit.Assert
import org.junit.Test
import java.util.*

class ConvertersTest {

    private val cal = Calendar.getInstance().apply {
        set(Calendar.YEAR, 1994)
        set(Calendar.MONTH, Calendar.DECEMBER)
        set(Calendar.DAY_OF_MONTH, 3)
    }

    @Test
    fun calendarToDateString() {
        Assert.assertEquals("03/12/1994", Converters().calendarToString(cal))
    }

    @Test
    fun dateStringToCalendar() {
        Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), Converters().stringToCalendar("03/12/1994").get(Calendar.DAY_OF_MONTH))
        Assert.assertEquals(cal.get(Calendar.MONTH), Converters().stringToCalendar("03/12/1994").get(Calendar.MONTH))
        Assert.assertEquals(cal.get(Calendar.YEAR), Converters().stringToCalendar("03/12/1994").get(Calendar.YEAR))
    }

}