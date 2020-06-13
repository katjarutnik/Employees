package com.katja.employeeslist.ui.fragments.analytics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katja.employeeslist.data.repository.Repository
import com.katja.employeeslist.internal.lazyDeferred

class AnalyticsViewModel(private val repository: Repository) : ViewModel() {

    val employees by lazyDeferred {
        repository.getEmployees()
    }
}