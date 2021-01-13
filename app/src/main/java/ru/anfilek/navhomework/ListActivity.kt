package ru.anfilek.navhomework


import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListActivity : AppCompatActivity() {


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


    private fun startCameraFeature() {
        val permissionStatus =
            ActivityCompat.checkSelfPermission(this, "android.permission.CAMERA")

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf("android.permission.CAMERA"),
                getString(R.string.request_code).toInt()
            )
        }
    }


    private fun startItemActivity() {

        val intentToItemActivity = Intent(this, ItemActivity::class.java)
        finish()
        startActivity(intentToItemActivity)
    }

    private fun openCamera() {
        if (findViewById<RadioButton>(R.id.radioButton3).isChecked) {
            val intentToCamera = Intent(this, CameraActivity::class.java)
            startActivity(intentToCamera)
        } else {
            val intentToCamera = Intent(this, AnotherCamera::class.java)
            startActivity(intentToCamera)
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            getString(R.string.request_code).toInt() -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission granted
                    openCamera()
                } else {
                    val myDialogFragment = MyDialogFragment()
                    val manager = supportFragmentManager
                    myDialogFragment.show(manager, "myDialog")
                }
            }

        }
    }
}