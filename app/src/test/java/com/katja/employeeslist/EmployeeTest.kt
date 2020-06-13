package com.katja.employeeslist

import com.katja.employeeslist.data.db.entity.Employee
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class EmployeeTest {

    private lateinit var employee: Employee

    @Before fun setUp() {
        employee = Employee(
            0,
            "Katja Rutnik",
            "03/12/1994",
            "female",
            5000.0
        )
    }

    @Test fun test_toString() {
        Assert.assertEquals("Katja Rutnik", employee.toString())
    }

    @Test fun test_isFemale() {
        Assert.assertTrue(employee.gender == "female")
    }

    @Test fun test_isNotMale() {
        Assert.assertFalse(employee.gender == "male")
    }

}