package com.example.myapplication.data

import java.util.UUID

data class Guru(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val skills: List<String> = emptyList(),
    val village: String = "",
    val availableHours: String = "",
    val bio: String = "",
    val rating: Double = 0.0,
    val thankYouCount: Int = 0
)

data class Session(
    val id: String = UUID.randomUUID().toString(),
    val guruId: String = "",
    val guruName: String = "",
    val subject: String = "",
    val date: String = "",
    val time: String = "",
    val location: String = "Samudaya Bhavana"
)

data class Appreciation(
    val id: String = UUID.randomUUID().toString(),
    val studentName: String = "",
    val guruId: String = "",
    val message: String = "",
    val date: Long = System.currentTimeMillis()
)
