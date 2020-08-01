package com.example.second_portfolio_proj.activities
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.second_portfolio_proj.AppDatabase
import com.example.second_portfolio_proj.MyApplication
import com.example.second_portfolio_proj.R
import com.example.second_portfolio_proj.presenters.RegLogActivityPresenter
import com.example.second_portfolio_proj.views.RegLogActivityView
import kotlinx.android.synthetic.main.activity_reg.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import javax.inject.Inject


class RegLogActivity:MvpAppCompatActivity(), RegLogActivityView, View.OnClickListener {
    @InjectPresenter
    lateinit var regActivityPresenter: RegLogActivityPresenter


    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)
        (application as MyApplication).getComponent().inject(this)
        regBtn.setOnClickListener(this)
        loginBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.regBtn->{
                val intentReg= Intent(this, RegistrationActivity::class.java)
                startActivity(intentReg)
            }
            R.id.loginBtn-> {
                val intentLog= Intent(this, AfterLoginActivity::class.java)
                GlobalScope.launch (Dispatchers.IO) {
                    if (db.userDao()?.getByTwoParams(
                            login = loginET.text.toString(),
                            password = passwET.text.toString()
                        ) != null
                    ) { //Если есть в базе - авторизуемся

                            startActivity(intentLog)


                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(applicationContext, "You must register", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }


                }
            }

        }
    }

    override fun onStop() {
        super.onStop()
        if (checkBox.isChecked) {
            regActivityPresenter.saveLogAndPassw(sharedPreferences,loginET.text.toString(),passwET.text.toString(),true)
        }
        else {
            regActivityPresenter.removeInfo(sharedPreferences)
        }
    }

    override fun onResume() {
        super.onResume()
        if (regActivityPresenter.isCheckBox(sp = sharedPreferences)) {
            checkBox.isChecked=true
            loginET.setText(sharedPreferences.getString(RegLogActivityPresenter.LOGIN,"default"))
            passwET.setText(sharedPreferences.getString(RegLogActivityPresenter.PASSWORD,"default"))
        }
    }

}