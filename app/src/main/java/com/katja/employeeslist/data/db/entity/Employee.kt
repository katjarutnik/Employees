package com.katja.employeeslist.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var employeeId: Int,
    val name: String,
    val birthday: String, // "day/month/year", eg. "03/12/1994"
    val gender: String,
    val salary: Double
) {
    override fun toString() = name
}
