package com.example.second_portfolio_proj

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.second_portfolio_proj.API.UserDAO


@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO?
}