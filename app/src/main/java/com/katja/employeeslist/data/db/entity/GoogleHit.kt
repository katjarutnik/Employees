package com.katja.employeeslist.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "google_hits")
data class GoogleHit(
    @PrimaryKey(autoGenerate = true)
    var googleHitId: Int = 0,
    val employeeOwnerId: Int?,
    val title: String
)