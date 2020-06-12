package com.katja.employeeslist.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey @ColumnInfo(name = "id") val employeeId: String,
    val name: String,
    val birthday: Calendar = Calendar.getInstance(),
    val gender: String,
    val salary: Double
) {
    override fun toString() = name
}
