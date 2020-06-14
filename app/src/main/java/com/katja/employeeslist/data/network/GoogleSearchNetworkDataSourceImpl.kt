package com.katja.employeeslist.data.network

import androidx.lifecycle.MutableLiveData

class GoogleSearchNetworkDataSourceImpl(
    private val googleSearchApiService : GoogleSearchApiService
) : GoogleSearchNetworkDataSource {

    override val downloadedGoogleHits: MutableLiveData<GoogleSearchResponse> = MutableLiveData()

    override suspend fun fetchGoogleHits(query: String) {
        val fetched = googleSearchApiService.getGoogleHits(query)
        downloadedGoogleHits.postValue(fetched)
    }

}