package ru.anfilek.navhomework

import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.annotation.RequiresApi
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
            ContextCompat.checkSelfPermission(this, "android.permission.CAMERA")

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf("android.permission.CAMERA"),
                1
            )
        }
    }

    private fun startItemActivity() {

        val intentToItemActivity = Intent(this, ItemActivity::class.java)
        finish()
        startActivity(intentToItemActivity)
    }

    private fun openCamera(){
        if(findViewById<RadioButton>(R.id.radioButton3).isChecked){
            val intentToCamera = Intent(this, CameraActivity::class.java)
            startActivity(intentToCamera)
        }
        else{
            val intentToCamera = Intent(this, AnotherCamera ::class.java)
            startActivity(intentToCamera)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission granted
                    openCamera()
                } else {
                    // permission denied
                }
                return
            }
        }
    }


}