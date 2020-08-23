package com.example.second_portfolio_proj.API

import androidx.room.*
import com.example.second_portfolio_proj.User


@Dao
interface UserDAO {
    @Query("SELECT * FROM User")
    suspend fun getAll(): List<User?>?

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getById(id: Long): User?

    @Insert
    suspend fun insert(User: User?)

    @Update
    suspend fun update(User: User?)

    @Delete
    suspend fun delete(User: User?)

    @Query("SELECT * FROM User WHERE name = :name AND surname=:surname AND password=:password AND login=:login")
    suspend fun getByParams(name:String,login:String,password:String,surname:String): User?

    @Query("SELECT * FROM User WHERE password=:password AND login=:login")
    suspend fun getByTwoParams(login:String,password:String): User?



}