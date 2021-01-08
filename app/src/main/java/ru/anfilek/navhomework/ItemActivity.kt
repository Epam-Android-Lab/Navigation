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

        findViewById<Button>(R.id.startAgainButton).setOnClickListener {
            startMeAgain()
        }

        findViewById<Button>(R.id.logout).setOnClickListener {
            logout()
        }
        renderItemId(intent.getIntExtra("tvItemId", 0))
    }

    private fun renderItemId(id: Int) {
        // get id from arguments and set it in the tvItemId
        findViewById<TextView>(R.id.tvItemId).text = "$id"
    }

    private fun startMeAgain() {
        // start the activity again.
        // For user it should look like activity is just updated
        // Do not forget to randomise new itemIt and put it as an argument.
        Intent(this, ItemActivity::class.java).apply {
            val tvItemId = findViewById<TextView>(R.id.tvItemId).text.toString()
            putExtra("tvItemId", tvItemId.toInt() + 1)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    private fun logout() {
        // go to login screen
        // pay attention to backstack
        userLogin.setUserLoggedOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}