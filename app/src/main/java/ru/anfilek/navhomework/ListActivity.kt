package ru.anfilek.navhomework

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random
import kotlin.random.nextInt

class ListActivity : AppCompatActivity(), IButtonRetryListener {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        findViewById<FloatingActionButton>(R.id.fabStartCamera).setOnClickListener {
            startCameraFeature()
        }

        findViewById<Button>(R.id.buttonItem).setOnClickListener {
            startItemActivity()
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun startCameraFeature() {
        // check camera permission
        // handle the check result
        // show dialog if it is needed
        // feel free to customise the button if it is needed
        check()
    }

    private fun startCameraActivity() {
        startActivity(Intent(this, CameraActivity::class.java))
    }

    private fun startItemActivity() {
        val intent = Intent(this, ItemActivity::class.java).apply {
            putExtra(RANDOM_ITEM_KEY, Random.nextInt(1..10))
        }
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun check() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                startCameraActivity()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showExplanation(withRationale = true)
            }
            else -> {
                requestPermission()
            }
        }
    }

    private fun requestPermission() = ActivityCompat.requestPermissions(
        this,
        arrayOf(
            Manifest.permission.CAMERA
        ),
        CAMERA_PERMISSION_REQUEST_CODE
    )

    private fun showExplanation(withRationale: Boolean = false){
        ExplanationDialogFragment.newInstance(withRationale).show(supportFragmentManager, null)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults.first() == PackageManager.PERMISSION_GRANTED)
                ) {
                    startCameraActivity()
                } else {
                    showExplanation()
                }
                return
            }
        }
    }

    override fun clicked() {
        requestPermission()
    }

    companion object {
        const val CAMERA_PERMISSION_REQUEST_CODE = 321
        const val RANDOM_ITEM_KEY = "RANDOM_ITEM_KEY"
    }
}