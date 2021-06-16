package com.example.fasol

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson

@SuppressLint("StaticFieldLeak")
object ProfileManager {
    lateinit var context: Context
    private lateinit var user: User
    private var wasCleared = true

    fun isUserExist(): Boolean {
        val smth = context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
        return smth?.contains("user")!!
    }

    fun getCurrentUser(): User {//возвращает строку из памяти jsona
        if (!this::user.isInitialized || wasCleared) {
            if (isUserExist()) {
                val userStr = context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                    ?.getString("user", "")
                user = Gson().fromJson(userStr, User::class.java) // десиалерищзует
                wasCleared = false
            }
        }
        return user
    }

    fun clear() {//очистка данных из памяти
        context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE).edit().remove("user")
            .apply()
        wasCleared = true
    }
}