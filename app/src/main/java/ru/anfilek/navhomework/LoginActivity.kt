package ru.anfilek.navhomework

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ru.anfilek.navhomework.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val userLogin: UserLogin by lazy { UserLogin(this) }

    private val binding: ActivityLoginBinding by lazy {
        val tmpBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(tmpBinding.root)
        tmpBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.button.setOnClickListener {
            performLogin()
        }
        checkLoginFlow()
    }

    private fun checkLoginFlow() {
        if (userLogin.isUserLoggedIn()) {
            // close this activity and open ListActivity
            startActivity(Intent(this, ListActivity::class.java))
            finish()
        }
    }

    private fun performLogin() {
        userLogin.setUserLoggedIn()
        // close this activity and open ListActivity
        startActivity(Intent(this, ListActivity::class.java))
        finish()
    }
}