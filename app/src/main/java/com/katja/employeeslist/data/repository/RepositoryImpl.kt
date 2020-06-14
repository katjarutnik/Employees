package com.katja.employeeslist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.katja.employeeslist.data.db.EmployeeDao
import com.katja.employeeslist.data.db.entity.Employee
import com.katja.employeeslist.data.network.GoogleSearchNetworkDataSource
import com.katja.employeeslist.data.network.GoogleSearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(
    private val employeeDao: EmployeeDao,
    private val googleSearchNetworkDataSource: GoogleSearchNetworkDataSource
) : Repository {

    private val downloadedGoogleHits: MutableLiveData<GoogleSearchResponse> = MutableLiveData()

    init {
        googleSearchNetworkDataSource.downloadedGoogleHits.observeForever { response ->
            downloadedGoogleHits.postValue(response)
        }
    }

    override suspend fun getEmployees() : LiveData<List<Employee>> {
        return withContext(Dispatchers.IO) {
            return@withContext employeeDao.getEmployees()
        }
    }

    override suspend fun getEmployee(employeeId: Int) : LiveData<Employee> {
        return withContext(Dispatchers.IO) {
            return@withContext employeeDao.getEmployee(employeeId)
        }
    }

    override suspend fun insertEmployees(employees: List<Employee>) {
        return withContext(Dispatchers.IO) {
            return@withContext employeeDao.insertAll(employees)
        }
    }

    override suspend fun getGoogleHits(query: String) : LiveData<GoogleSearchResponse> {
        return withContext(Dispatchers.IO) {
            fetchGoogleHits(query)
            return@withContext downloadedGoogleHits
        }
    }

    private suspend fun fetchGoogleHits(query: String) {
        googleSearchNetworkDataSource.fetchGoogleHits(query)
    }
}