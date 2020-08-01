package com.example.second_portfolio_proj.activities

import android.os.Bundle
import com.example.second_portfolio_proj.R
import moxy.MvpAppCompatActivity

class AfterLoginActivity:MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_login)
    }
}