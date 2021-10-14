package com.android.andersenfragments

import java.util.*

data class Contact constructor(
    val name: String,
    val surname: String,
    val phoneNumber: String,
) {

    val contactUID = UUID.randomUUID()

    fun getNameAndSurname() = "$name $surname"
}