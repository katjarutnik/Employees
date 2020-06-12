package com.katja.employeeslist

import com.katja.employeeslist.data.db.entity.Employee
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class EmployeeTest {

    private lateinit var employee: Employee

    private val birthday: Calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, 1994)
        set(Calendar.MONTH, Calendar.DECEMBER)
        set(Calendar.DAY_OF_MONTH, 3)
    }

    @Before fun setUp() {
        employee = Employee("0", "Katja Rutnik", birthday, "female", 5000.0)
    }

    @Test fun test_toString() {
        Assert.assertEquals("Katja Rutnik", employee.toString())
    }

    @Test fun test_isFemale() {
        Assert.assertTrue(employee.gender == "female")
    }

    @Test fun test_isMale() {
        Assert.assertFalse(employee.gender == "male")
    }

}