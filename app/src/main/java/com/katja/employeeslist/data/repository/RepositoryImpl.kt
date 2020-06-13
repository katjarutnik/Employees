package com.katja.employeeslist.data.repository

import androidx.lifecycle.LiveData
import com.katja.employeeslist.data.db.EmployeeDao
import com.katja.employeeslist.data.db.entity.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(private val employeeDao: EmployeeDao) : Repository {

    override suspend fun getEmployees() : LiveData<List<Employee>> {
        return withContext(Dispatchers.IO) {
            return@withContext employeeDao.getEmployees()
        }
    }

    override suspend fun getEmployee(employeeId: Int) : LiveData<Employee> {
        return withContext(Dispatchers.IO) {
            return@withContext employeeDao.getEmployee(employeeId)
        }
    }
}