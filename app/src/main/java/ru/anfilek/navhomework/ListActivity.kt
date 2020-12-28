package ru.anfilek.navhomework

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.Manifest.permission.CAMERA

class ListActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
    }

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
        return when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.M ->
                startActivity(Intent(this, CameraActivity::class.java))
            ContextCompat.checkSelfPermission(this, CAMERA)
                    == PackageManager.PERMISSION_GRANTED ->
                startActivity(Intent(this, CameraActivity::class.java))
            else -> showDialogPermissionInterpretation(CAMERA)
        }
    }

    private fun startItemActivity() {
        startActivity(Intent(this, ItemActivity::class.java))
    }


    private fun showDialogPermissionInterpretation(permission: String) {
        AlertDialog.Builder(this)
                .setTitle("Camera permission needed")
                .setMessage("To continue using the application, access to the camera is required")
                .setNegativeButton("Deny access") { _, _ -> showOptionPermissionDecline(permission) }
                .setPositiveButton("Give access") { _, _ -> showOptionPermissionAccepted(permission) }
                .show()
    }

    private fun showOptionPermissionDecline(permission: String) {
        Toast.makeText(this, "No permission to use the camera has been granted", Toast.LENGTH_SHORT)
                .show()
    }

    private fun showOptionPermissionAccepted(permission: String) {
        ActivityCompat.requestPermissions(this, arrayOf(permission), CAMERA_PERMISSION_CODE)
    }

}