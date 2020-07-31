package com.example.second_portfolio_proj.activities
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.view.View
import com.example.second_portfolio_proj.MyApplication
import com.example.second_portfolio_proj.R
import com.example.second_portfolio_proj.presenters.RegActivityPresenter
import com.example.second_portfolio_proj.views.RegActivityView
import kotlinx.android.synthetic.main.activity_reg.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import javax.inject.Inject


class RegLogActivity:MvpAppCompatActivity(), RegActivityView, View.OnClickListener {
    @InjectPresenter
    lateinit var regActivityPresenter: RegActivityPresenter


    @Inject
    lateinit var sharedPreferences: SharedPreferences

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
                startActivity(intentLog)
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
            loginET.setText(sharedPreferences.getString(RegActivityPresenter.LOGIN,"default"))
            passwET.setText(sharedPreferences.getString(RegActivityPresenter.PASSWORD,"default"))
        }
    }

}