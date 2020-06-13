package com.katja.employeeslist

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.katja.employeeslist.data.db.AppDatabase
import com.katja.employeeslist.data.repository.Repository
import com.katja.employeeslist.data.repository.RepositoryImpl
import com.katja.employeeslist.ui.fragments.analytics.AnalyticsViewModelFactory
import com.katja.employeeslist.ui.fragments.list.ListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class EmployeesApp : Application(), KodeinAware {
    override val kodein: Kodein =Kodein.lazy {
        import(androidXModule(this@EmployeesApp))

        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { instance<AppDatabase>().employeeDao() }
        bind<Repository>() with singleton { RepositoryImpl(instance()) }
        bind() from provider { ListViewModelFactory(instance()) }
        bind() from provider { AnalyticsViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}