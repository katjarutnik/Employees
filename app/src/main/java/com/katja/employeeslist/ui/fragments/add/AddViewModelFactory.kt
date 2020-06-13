package com.katja.employeeslist.ui.fragments.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.katja.employeeslist.data.repository.Repository

class AddViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddViewModel(repository) as T
    }
}