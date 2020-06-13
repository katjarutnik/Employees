package com.katja.employeeslist.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val employeeId: Int,
    val name: String,
    val birthday: String,
    val gender: String,
    val salary: String
) {
    override fun toString() = name
}
