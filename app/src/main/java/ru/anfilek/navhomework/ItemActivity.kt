package ru.anfilek.navhomework

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_item.*
import kotlin.random.Random
import kotlin.random.nextInt

class ItemActivity : AppCompatActivity() {

    private val userLogin: UserLogin by lazy { UserLogin(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        renderItemId()

        startAgainButton.setOnClickListener {
            startMeAgain()
        }

        logout.setOnClickListener {
            logout()
        }
    }

    private fun renderItemId() {
        // get id from arguments and set it in the tvItemId
        tvItemId.text = intent.getStringExtra("randomNumber")
    }

    private fun startMeAgain() {
        //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val intent = Intent(this, ItemActivity::class.java)
        val myRandom = Random.nextInt(0..100)
        intent.putExtra("randomNumber","$myRandom")
        finish()
        startActivity(intent)
    }

    private fun logout() {
        userLogin.setUserLoggedOut()
        // go to login screen
        // pay attention to backstack
        intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        finish()
        startActivity(intent)
    }
}