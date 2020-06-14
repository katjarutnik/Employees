package com.katja.employeeslist.ui.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.katja.employeeslist.data.repository.Repository

class ProfileViewModelFactory(private val repository: Repository,
                              private val employeeId: Int) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository, employeeId) as T
    }
}