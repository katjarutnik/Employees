package com.katja.employeeslist.utilities

import com.katja.employeeslist.data.db.entity.Employee
import java.util.*

/**
 * [Calendar] object used for tests.
 */
val testCalendar: Calendar = Calendar.getInstance().apply {
    set(Calendar.YEAR, 1994)
    set(Calendar.MONTH, Calendar.DECEMBER)
    set(Calendar.DAY_OF_MONTH, 3)
}

/**
 * [Employee] objects used for tests.
 */
val testEmployees = arrayListOf(
    Employee("1", "A", testCalendar, "male", 1111.1),
    Employee("2", "B", testCalendar, "female", 2222.2),
    Employee("3", "C", testCalendar, "male", 3333.3)
)
val testEmployee = testEmployees[0]