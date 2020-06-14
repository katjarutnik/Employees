package com.katja.employeeslist.data.network

import com.katja.employeeslist.internal.Keys
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://www.googleapis.com/customsearch/"
const val GOOGLE_SEARCH_RESULTS_COUNT = "5"
const val GOOGLE_SEARCH_FIELDS = "items(title)"

data class GoogleHit(val title: String)

data class GoogleSearchResponse(val items: List<GoogleHit>)

interface GoogleSearchApiService {

    @GET("v1")
    suspend fun getGoogleHits(@Query("q") query: String): GoogleSearchResponse

    companion object {
        operator fun invoke() : GoogleSearchApiService {

            val requestInterceptor = Interceptor { chain ->

                val url = chain.request().url().newBuilder()
                    .addQueryParameter("key", Keys.apiKey())
                    .addQueryParameter("cx", Keys.cxKey())
                    .addQueryParameter("fields", GOOGLE_SEARCH_FIELDS)
                    .addQueryParameter("num", GOOGLE_SEARCH_RESULTS_COUNT)
                    .build()

                val request = chain.request().newBuilder().url(url).build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor).build()

            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(GoogleSearchApiService::class.java)
        }

    }
}