package ru.anfilek.navhomework

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val userLogin: UserLogin by lazy { UserLogin(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button.setOnClickListener {
            performLogin()
            checkLoginFlow()
        }
    }

    private fun checkLoginFlow() {
        if (userLogin.isUserLoggedIn()) {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
            finish()
            // close this activity and open ListActivity
        }
    }

    private fun performLogin() {
        userLogin.setUserLoggedIn()
        // close this activity and open ListActivity
    }
}