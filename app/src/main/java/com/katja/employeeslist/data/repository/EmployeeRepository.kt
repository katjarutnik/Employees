package com.katja.employeeslist.data.repository

import com.katja.employeeslist.data.db.EmployeeDao

class EmployeeRepository private constructor(private val employeeDao: EmployeeDao) {

    fun getEmployees() = employeeDao.getEmployees()

    fun getEmployee(employeeId: String) = employeeDao.getEmployee(employeeId)

    companion object {

        @Volatile private var instance: EmployeeRepository? = null

        fun getInstance(employeeDao: EmployeeDao) = instance ?: synchronized(this) {
                instance ?: EmployeeRepository(employeeDao).also { instance = it }
            }
    }
}
