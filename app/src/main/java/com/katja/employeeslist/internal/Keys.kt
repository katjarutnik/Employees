package com.katja.employeeslist.internal

object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String

    external fun cxKey(): String
}