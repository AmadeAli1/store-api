package com.amade.storeapi.response

import org.springframework.http.HttpStatus
import java.sql.Timestamp

data class Message(
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis()),
    val message: String,
    val status: String = HttpStatus.OK.name,
) {
}