package com.katja.employeeslist

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.katja.employeeslist.data.db.AppDatabase
import com.katja.employeeslist.data.network.GoogleSearchApiService
import com.katja.employeeslist.data.network.GoogleSearchNetworkDataSource
import com.katja.employeeslist.data.network.GoogleSearchNetworkDataSourceImpl
import com.katja.employeeslist.data.repository.Repository
import com.katja.employeeslist.data.repository.RepositoryImpl
import com.katja.employeeslist.ui.fragments.add.AddViewModelFactory
import com.katja.employeeslist.ui.fragments.analytics.AnalyticsViewModelFactory
import com.katja.employeeslist.ui.fragments.list.ListViewModelFactory
import com.katja.employeeslist.ui.fragments.profile.ProfileViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.Multi2
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class EmployeesApp : Application(), KodeinAware {
    override val kodein: Kodein =Kodein.lazy {
        import(androidXModule(this@EmployeesApp))

        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { instance<AppDatabase>().employeeDao() }

        bind() from singleton { GoogleSearchApiService() }
        bind<GoogleSearchNetworkDataSource>() with singleton {
            GoogleSearchNetworkDataSourceImpl(instance()) }

        bind<Repository>() with singleton { RepositoryImpl(instance(), instance()) }

        bind() from provider { ListViewModelFactory(instance()) }
        bind() from provider { AnalyticsViewModelFactory(instance()) }
        bind() from provider { AddViewModelFactory(instance()) }

        bind<ProfileViewModelFactory>() with factory { employeeId: Int, employeeName: String ->
            ProfileViewModelFactory(instance(), employeeId, employeeName) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}