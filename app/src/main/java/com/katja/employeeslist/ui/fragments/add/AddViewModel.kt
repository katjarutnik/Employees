package com.katja.employeeslist.ui.fragments.add

import androidx.lifecycle.ViewModel
import com.katja.employeeslist.data.db.entity.Employee
import com.katja.employeeslist.data.repository.Repository

class AddViewModel(private val repository: Repository) : ViewModel() {

    suspend fun addEmployee(employee: Employee) {
        repository.insertEmployees(listOf(employee))
    }

}