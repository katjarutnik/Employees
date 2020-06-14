package com.katja.employeeslist.data.network

import androidx.lifecycle.LiveData

interface GoogleSearchNetworkDataSource {

    val downloadedGoogleHits : LiveData<GoogleSearchResponse>

    suspend fun fetchGoogleHits(query: String)
}