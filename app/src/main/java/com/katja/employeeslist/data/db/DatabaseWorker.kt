package com.katja.employeeslist.data.db

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.katja.employeeslist.data.db.entity.Employee
import kotlinx.coroutines.coroutineScope

class DatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(EMPLOYEE_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val employeeType = object : TypeToken<List<Employee>>() {}.type
                    val employeeList: List<Employee> = Gson().fromJson(jsonReader, employeeType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.employeeDao().insertAll(employeeList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "EmployeeDatabaseWorker"
    }
}