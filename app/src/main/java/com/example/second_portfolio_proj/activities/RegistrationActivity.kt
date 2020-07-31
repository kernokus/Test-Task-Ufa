package com.example.second_portfolio_proj.activities

import android.os.Bundle
import com.example.second_portfolio_proj.R
import moxy.MvpAppCompatActivity

class RegistrationActivity:MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }
}