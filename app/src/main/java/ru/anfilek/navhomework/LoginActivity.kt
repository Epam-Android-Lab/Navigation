package ru.anfilek.navhomework

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.anfilek.navhomework.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextTextPersonName: EditText
    private lateinit var button : Button
    private lateinit var editTextTextPassword : EditText

    private lateinit var binding: ActivityLoginBinding

    private val userLogin: UserLogin by lazy { UserLogin(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextTextPassword = findViewById(R.id.editTextTextPassword)
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName)
        button = findViewById(R.id.button)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        button.setOnClickListener {
            performLogin()
        }
        checkLoginFlow()
    }

    private fun checkLoginFlow() {
        if (userLogin.isUserLoggedIn()) {
            // close this activity and open ListActivity
            var toActivityListIntent: Intent = Intent(this, ListActivity::class.java)
            finish()
            startActivity(toActivityListIntent)

        }
    }

    private fun performLogin() {

        // close this activity and open ListActivity
        userLogin.setUserLoggedIn()

        if (editTextTextPersonName.text.isNotEmpty() && isValidEmail(editTextTextPersonName.text.toString()) &&
            editTextTextPassword.text.isNotEmpty()
        ) {
            var toActivityListIntent: Intent = Intent(this, ListActivity::class.java)
            finish()
            startActivity(toActivityListIntent)
        } else {
            var toast = Toast.makeText(
                this,
                "Не все поля введены",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}