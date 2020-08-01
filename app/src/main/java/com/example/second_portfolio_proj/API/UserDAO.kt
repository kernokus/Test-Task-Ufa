package com.example.second_portfolio_proj.API

import androidx.room.*
import com.example.second_portfolio_proj.User


@Dao
interface UserDAO {
    @Query("SELECT * FROM User")
    fun getAll(): List<User?>?

    @Query("SELECT * FROM User WHERE id = :id")
    fun getById(id: Long): User?

    @Insert
    fun insert(User: User?)

    @Update
    fun update(User: User?)

    @Delete
    fun delete(User: User?)

    @Query("SELECT * FROM User WHERE name = :name AND surname=:surname AND password=:password AND login=:login")
    fun getByParams(name:String,login:String,password:String,surname:String): User?

    @Query("SELECT * FROM User WHERE password=:password AND login=:login")
    fun getByTwoParams(login:String,password:String): User?



}