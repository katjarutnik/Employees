package com.katja.employeeslist.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.katja.employeeslist.data.db.AppDatabase
import com.katja.employeeslist.data.db.EmployeeDao
import com.katja.employeeslist.data.db.entity.Employee
import com.katja.employeeslist.utilities.getValue
import com.katja.employeeslist.utilities.testCalendar
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class EmployeeDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var employeeDao: EmployeeDao

    private val employeeA = Employee("1", "A", testCalendar, "male", 1111.1)
    private val employeeB = Employee("2", "B", testCalendar, "female", 2222.2)
    private val employeeC = Employee("3", "C", testCalendar, "male", 3333.3)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        employeeDao = database.employeeDao()

        // Insert employees in non-alphabetical order to test that results are sorted by name
        employeeDao.insertAll(listOf(employeeB, employeeC, employeeA))
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun testGetEmployees() {
        val employeeList = getValue(employeeDao.getEmployees())
        assertThat(employeeList.size, equalTo(3))

        // Ensure employee list is sorted by name
        compareEmployees(employeeList[0], employeeA)
        compareEmployees(employeeList[1], employeeB)
        compareEmployees(employeeList[2], employeeC)
    }

    @Test fun testGetEmployee() {
        compareEmployees(getValue(employeeDao.getEmployee(employeeA.employeeId)), employeeA)
    }

    private fun compareEmployees(actual: Employee, expected: Employee) {
        assertThat(actual.employeeId, equalTo(expected.employeeId))
        assertThat(actual.name, equalTo(expected.name))
        assertThat(actual.gender, equalTo(expected.gender))
        assertThat(actual.birthday.get(Calendar.DAY_OF_MONTH), equalTo(expected.birthday.get(Calendar.DAY_OF_MONTH)))
        assertThat(actual.birthday.get(Calendar.MONTH), equalTo(expected.birthday.get(Calendar.MONTH)))
        assertThat(actual.birthday.get(Calendar.YEAR), equalTo(expected.birthday.get(Calendar.YEAR)))
        assertThat(actual.salary, equalTo(expected.salary))
    }
}