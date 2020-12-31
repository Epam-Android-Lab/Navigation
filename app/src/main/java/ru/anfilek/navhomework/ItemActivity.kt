package ru.anfilek.navhomework

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.anfilek.navhomework.databinding.ActivityItemBinding
import kotlin.random.Random
import kotlin.random.nextInt

class ItemActivity : AppCompatActivity() {

    private val userLogin: UserLogin by lazy { UserLogin(this) }

    private val binding: ActivityItemBinding by lazy {
        val tmpBinding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(tmpBinding.root)
        tmpBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        renderItemId()

        binding.startAgainButton.setOnClickListener {
            startMeAgain()
        }

        binding.logout.setOnClickListener {
            logout()
        }
    }

    private fun renderItemId() {
        // get id from arguments and set it in the tvItemId
        val itemId = intent.getIntExtra(ListActivity.RANDOM_ITEM_KEY, 0)
        binding.tvItemId.text = itemId.toString()
    }

    private fun startMeAgain() {
        // start the activity again.
        // For user it should look like activity is just updated
        // Do not forget to randomise new itemIt and put it as an argument.
        val intent = Intent(this, ItemActivity::class.java).apply {
            putExtra(ListActivity.RANDOM_ITEM_KEY, Random.nextInt(1..10))
        }
        finish()
        startActivity(intent)
    }

    private fun logout() {
        userLogin.setUserLoggedOut()
        // go to login screen
        // pay attention to backstack
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)

        finish()
    }
}