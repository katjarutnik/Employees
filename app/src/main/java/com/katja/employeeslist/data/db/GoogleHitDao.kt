package com.katja.employeeslist.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.katja.employeeslist.data.db.entity.GoogleHit


@Dao
interface GoogleHitDao {
    @Query("SELECT * FROM google_hits")
    fun getAll(): LiveData<List<GoogleHit>>

    @Query("SELECT * FROM google_hits WHERE googleHitId = :googleHitId")
    fun getGoogleHit(googleHitId: Int): LiveData<GoogleHit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(googleHits: List<GoogleHit>)

    @Query("DELETE FROM google_hits WHERE employeeOwnerId = :employeeId")
    fun delete(employeeId: Int?)
}