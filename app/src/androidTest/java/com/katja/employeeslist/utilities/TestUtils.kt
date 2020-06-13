package com.katja.employeeslist.utilities

import com.katja.employeeslist.data.db.entity.Employee

/**
 * [Employee] objects used for tests.
 */
val testEmployees = arrayListOf(
    Employee(
        1,
        "A",
        "1/1/2010",
        "male",
        1111.1
    ),
    Employee(
        2,
        "B",
        "2/2/2020",
        "female",
        2222.2
    ),
    Employee(
        3,
        "C",
        "3/3/3030",
        "male",
        3333.3
    )
)
val testEmployee = testEmployees[0]