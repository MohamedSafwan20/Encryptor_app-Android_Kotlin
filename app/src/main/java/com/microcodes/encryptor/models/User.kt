package com.microcodes.encryptor.models

data class User(
    val deviceId: String,
    val password: String,
    val migrations: Map<String, String> = emptyMap()
)
