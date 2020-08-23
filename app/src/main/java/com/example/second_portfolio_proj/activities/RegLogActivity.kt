package com.example.second_portfolio_proj.activities
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.second_portfolio_proj.AppDatabase
import com.example.second_portfolio_proj.MyApplication
import com.example.second_portfolio_proj.R
import com.example.second_portfolio_proj.presenters.RegLogActivityPresenter
import com.example.second_portfolio_proj.views.RegLogActivityView
import kotlinx.android.synthetic.main.activity_reg.*
import kotlinx.coroutines.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class RegLogActivity:MvpAppCompatActivity(), RegLogActivityView, View.OnClickListener,View.OnFocusChangeListener ,CoroutineScope{
    @InjectPresenter
    lateinit var regActivityPresenter: RegLogActivityPresenter

    override val coroutineContext: CoroutineContext = SupervisorJob() +Dispatchers.Main.immediate

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


        loginET.onFocusChangeListener = this
        passwET.onFocusChangeListener = this

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.regBtn->{
                val intentReg= Intent(this, RegistrationActivity::class.java)
                startActivity(intentReg)
            }
            R.id.loginBtn-> {
                val intentLog= Intent(this, AfterLoginActivity::class.java)
                launch  {
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


    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if(v?.id==R.id.loginET || v?.id==R.id.passwET) {
            if (!hasFocus) {
                hideKeyboard(v)
            }
        }
    }

}