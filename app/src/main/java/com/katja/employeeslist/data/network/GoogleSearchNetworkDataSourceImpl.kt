package com.katja.employeeslist.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.katja.employeeslist.internal.NoConnectivityException

class GoogleSearchNetworkDataSourceImpl(
    private val googleSearchApiService : GoogleSearchApiService
) : GoogleSearchNetworkDataSource {

    override val downloadedGoogleHits: MutableLiveData<GoogleSearchResponse> = MutableLiveData()

    override suspend fun fetchGoogleHits(query: String) {
        try {
            val fetched = googleSearchApiService.getGoogleHits(query)
            downloadedGoogleHits.postValue(fetched)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

}