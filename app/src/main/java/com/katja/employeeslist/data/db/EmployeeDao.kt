package com.katja.employeeslist.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.katja.employeeslist.data.db.entity.Employee
import com.katja.employeeslist.data.db.entity.EmployeeWithGoogleHits

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees ORDER BY name")
    fun getEmployees(): LiveData<List<Employee>>

    @Query("SELECT * FROM employees WHERE employeeId = :employeeId")
    fun getEmployee(employeeId: Int): LiveData<Employee>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(employees: List<Employee>)

    @Transaction
    @Query("SELECT * FROM employees WHERE employeeId = :employeeId")
    fun getEmployeeWithGoogleHits(employeeId: Int): LiveData<EmployeeWithGoogleHits>
}
