package com.katja.employeeslist.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class EmployeeWithGoogleHits(
    @Embedded val employee: Employee,
    @Relation(
        parentColumn = "employeeId",
        entityColumn = "employeeOwnerId"
    )
    val hits: List<GoogleHit>
)