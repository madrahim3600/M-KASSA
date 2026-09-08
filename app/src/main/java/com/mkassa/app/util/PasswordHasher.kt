package com.mkassa.app.util

import java.security.MessageDigest
import java.util.Base64

object PasswordHasher {
    fun hash(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = messageDigest.digest(password.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(hashedBytes)
    }

    fun verify(password: String, hash: String): Boolean {
        return hash(password) == hash
    }
}
