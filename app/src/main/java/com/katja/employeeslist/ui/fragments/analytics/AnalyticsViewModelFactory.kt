package com.katja.employeeslist.ui.fragments.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.katja.employeeslist.data.repository.Repository
import com.katja.employeeslist.ui.fragments.list.ListViewModel

class AnalyticsViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AnalyticsViewModel(repository) as T
    }
}