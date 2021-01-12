package ru.anfilek.navhomework

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ItemActivity : AppCompatActivity() {

    private val userLogin: UserLogin by lazy { UserLogin(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)



        findViewById<TextView>(R.id.tvItemId).text = intent.getDoubleExtra(
            "ID",
            getString(R.string.default_value_for_intent).toDouble()
        ).toString()

        findViewById<Button>(R.id.startAgainButton).setOnClickListener {
            startMeAgain()
        }

        findViewById<Button>(R.id.logout).setOnClickListener {
            logout()
        }
    }

    private fun renderItemId(): Double {
        return Math.random() * 10
    }

    private fun startMeAgain() {

        val reloadActivityIntent = Intent(this, ItemActivity::class.java)
        reloadActivityIntent.putExtra("ID", renderItemId())
        finish()
        startActivity(reloadActivityIntent)
    }

    private fun logout() {
        userLogin.setUserLoggedOut()
        val intentToLoginActivity = Intent(this, LoginActivity::class.java)
        finish()
        startActivity(intentToLoginActivity)
    }
}