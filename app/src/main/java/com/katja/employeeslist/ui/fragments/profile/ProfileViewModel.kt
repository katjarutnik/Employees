package com.katja.employeeslist.ui.fragments.profile

import androidx.lifecycle.ViewModel
import com.katja.employeeslist.data.repository.Repository
import com.katja.employeeslist.internal.lazyDeferred

class ProfileViewModel(
    private val repository: Repository,
    private val employeeId: Int,
    private val employeeName: String
): ViewModel() {

    val employee by lazyDeferred {
        repository.getEmployee(employeeId)
    }

    val googleHits by lazyDeferred {
        repository.getGoogleHits(employeeName)
    }
}