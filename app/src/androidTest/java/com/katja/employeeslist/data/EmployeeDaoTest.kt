package com.katja.employeeslist.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.katja.employeeslist.data.db.AppDatabase
import com.katja.employeeslist.data.db.EmployeeDao
import com.katja.employeeslist.data.db.entity.Employee
import com.katja.employeeslist.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmployeeDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var employeeDao: EmployeeDao

    private val employeeA = Employee(
        employeeId = 0,
        name = "A",
        birthday = "01/01/2010",
        gender = "male",
        salary = 1111.1
    )
    private val employeeB = Employee(
        employeeId = 1,
        name = "B",
        birthday = "02/02/2020",
        gender = "female",
        salary = 2222.2
    )
    private val employeeC = Employee(
        employeeId = 2,
        name = "C",
        birthday = "03/03/3030",
        gender = "male",
        salary = 3333.3
    )

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
        assertThat(employeeList[0].name, equalTo(employeeA.name))
        assertThat(employeeList[1].name, equalTo(employeeB.name))
        assertThat(employeeList[2].name, equalTo(employeeC.name))
    }

    @Test fun testGetEmployee() {
        assertThat(getValue(employeeDao.getEmployee(employeeA.employeeId)), equalTo(employeeA))
    }
}