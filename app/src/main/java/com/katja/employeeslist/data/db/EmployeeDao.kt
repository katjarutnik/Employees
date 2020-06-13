package com.katja.employeeslist.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.katja.employeeslist.data.db.entity.Employee

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees ORDER BY name")
    fun getEmployees(): LiveData<List<Employee>>

    @Query("SELECT * FROM employees WHERE id = :employeeId")
    fun getEmployee(employeeId: Int): LiveData<Employee>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(employees: List<Employee>)
}
