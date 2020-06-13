package com.katja.employeeslist.ui.list

import androidx.lifecycle.ViewModel
import com.katja.employeeslist.data.repository.Repository
import com.katja.employeeslist.internal.lazyDeferred

class ListViewModel(private val repository: Repository) : ViewModel() {

    val employeeList by lazyDeferred { repository.getEmployees() }
}