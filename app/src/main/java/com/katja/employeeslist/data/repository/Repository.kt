package com.katja.employeeslist.data.repository

import androidx.lifecycle.LiveData
import com.katja.employeeslist.data.db.entity.Employee
import com.katja.employeeslist.data.network.GoogleSearchResponse

interface Repository {
    suspend fun getEmployees() : LiveData<List<Employee>>
    suspend fun getEmployee(employeeId: Int) : LiveData<Employee>
    suspend fun insertEmployees(employees: List<Employee>)
    suspend fun getGoogleHits(query: String) : LiveData<GoogleSearchResponse>
}
