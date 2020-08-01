package com.example.second_portfolio_proj.activities

import android.os.Bundle
import android.util.Log

import android.widget.Toast
import com.example.second_portfolio_proj.AppDatabase
import com.example.second_portfolio_proj.MyApplication
import com.example.second_portfolio_proj.R
import com.example.second_portfolio_proj.User
import com.example.second_portfolio_proj.presenters.RegistrActivityPresenter
import com.example.second_portfolio_proj.views.RegistrationActivityView
import kotlinx.android.synthetic.main.activity_reg.regBtn
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import javax.inject.Inject

class RegistrationActivity:MvpAppCompatActivity(),RegistrationActivityView {


    @Inject
    lateinit var db: AppDatabase

    @InjectPresenter
    lateinit var regActivityPresenter: RegistrActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        (application as MyApplication).getComponent().inject(this)
        regBtn.setOnClickListener {
            if (isFieldsAreFill()) { //TODO вынести в презентер!!!
               GlobalScope.launch (Dispatchers.IO){
                   db.userDao()?.insert(getUserInfoFields())
                   if (db.userDao()?.getByParams(Name.text.toString(),login.text.toString(),SecName.text.toString(),Passw.text.toString()) !=null) {
                       Log.i("DATABASE",db.userDao()?.getAll().toString())
                       withContext(Dispatchers.Main){
                           Toast.makeText(applicationContext, "Successfully", Toast.LENGTH_SHORT).show()
                       }
                   }
                   else Toast.makeText(applicationContext, "Not successfully", Toast.LENGTH_SHORT).show()


               }
            }
            else {
                Toast.makeText(applicationContext, R.string.needToFillAll, Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun isFieldsAreFill():Boolean {
        if (login.text.toString()!="" && Name.text.toString()!="" && SecName.text.toString()!="" && Passw.text.toString()!="") {
            return true
        }
        return false
    }

    private fun getUserInfoFields(): User {
        return User(login = login.text.toString(),name = Name.text.toString(),password = Passw.text.toString(),surname = SecName.text.toString(),id = 0)
    }


}