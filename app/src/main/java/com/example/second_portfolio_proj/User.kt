package com.example.second_portfolio_proj

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
 class User (@PrimaryKey(autoGenerate = true)
             @ColumnInfo(name = "id")
             var id: Long,
             @ColumnInfo(name = "login")
                 var login: String,
             @ColumnInfo(name = "name")
                 var name: String,
             @ColumnInfo(name = "surname")
                 var surname : String,
             @ColumnInfo(name = "password")
                 var password: String) {

}
