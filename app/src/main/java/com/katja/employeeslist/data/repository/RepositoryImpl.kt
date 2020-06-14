package com.katja.employeeslist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.katja.employeeslist.data.db.EmployeeDao
import com.katja.employeeslist.data.db.GoogleHitDao
import com.katja.employeeslist.data.db.entity.Employee
import com.katja.employeeslist.data.db.entity.EmployeeWithGoogleHits
import com.katja.employeeslist.data.db.entity.GoogleHit
import com.katja.employeeslist.data.network.GoogleSearchNetworkDataSource
import com.katja.employeeslist.data.network.GoogleSearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryImpl(
    private val employeeDao: EmployeeDao,
    private val googleHitDao: GoogleHitDao,
    private val googleSearchNetworkDataSource: GoogleSearchNetworkDataSource
) : Repository {

    private val downloadedGoogleHits: MutableLiveData<GoogleSearchResponse> = MutableLiveData()

    private var currentEmployeeId : Int? = null

    init {
        googleSearchNetworkDataSource.downloadedGoogleHits.observeForever { response ->
            persistFetchedGoogleHits(response, currentEmployeeId)
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

    override suspend fun getGoogleHitsOnEmployee(employeeId: Int, query: String): LiveData<EmployeeWithGoogleHits> {
        currentEmployeeId = employeeId
        return withContext(Dispatchers.IO) {
            fetchGoogleHits(query)
            return@withContext employeeDao.getEmployeeWithGoogleHits(employeeId)
        }
    }

    private suspend fun fetchGoogleHits(query: String) {
        googleSearchNetworkDataSource.fetchGoogleHits(query)
    }

    private fun persistFetchedGoogleHits(fetchedGoogleHits: GoogleSearchResponse, employeeId: Int?) {
        fun deleteOldGoogleHits() {
            googleHitDao.delete(employeeId)
        }
        GlobalScope.launch(Dispatchers.IO) {
            deleteOldGoogleHits()
            val googleHitList = fetchedGoogleHits.toGoogleHitList(employeeId)
            googleHitDao.insertAll(googleHitList)
        }
    }

    private fun GoogleSearchResponse.toGoogleHitList(employeeId: Int?) : List<GoogleHit> {
        return this.items.map {
            GoogleHit(employeeOwnerId = employeeId, title = it.title)
        }
    }
}