package ru.anfilek.navhomework

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_list.*
import android.Manifest.permission.CAMERA as CAMERA_PERMISSION

class ListActivity : AppCompatActivity() {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 12548
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        fabStartCamera.setOnClickListener {
            val permission = CAMERA_PERMISSION
            handleCheckResult(permission, checkPermission(permission))
        }

        buttonItem.setOnClickListener() {
            startItemActivity()
        }
    }

    private fun checkPermission (permission: String) : CheckPermissionResult {
        return when {
            //проверка версии SDK
            Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> CheckPermissionResult.GRANTED

            //проверка наличия permission
            ContextCompat.checkSelfPermission(
                    this,
                    permission
            ) == PackageManager.PERMISSION_GRANTED -> CheckPermissionResult.GRANTED

            //Проверка, был ли permission отклонен
            //shouldShowRequestPermissionRationale возвращает true если не было выбрано "больше не спрашивать"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale (
                    permission
            ) -> CheckPermissionResult.NEED_TO_EXPLAIN
            else -> CheckPermissionResult.NEED_TO_REQUEST
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun handleCheckResult(permission: String, result: CheckPermissionResult) {
        when(result) {
            CheckPermissionResult.GRANTED -> startCameraActivity()
            CheckPermissionResult.DENIED -> failedGracefully()
            CheckPermissionResult.NEED_TO_REQUEST -> askForPermission(permission)
            CheckPermissionResult.NEED_TO_EXPLAIN -> showRationale()
        }
    }

    private fun startItemActivity() {
        intent = Intent(this, ItemActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showRationale() {
        AlertDialog.Builder(this)
                .setTitle("Camera permission")
                .setMessage("Camera permission is needed")
                .setPositiveButton("I understand") {
                    _, _ -> askForPermission(CAMERA_PERMISSION) }
                .show()
    }

    private fun startCameraActivity() {
        intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun failedGracefully() {
        AlertDialog.Builder(this)
                .setTitle("Camera permission")
                .setMessage("Camera permission was not granted")
                .setNegativeButton("I changed my mind") {_, _-> askForPermission(CAMERA_PERMISSION)}
                .setPositiveButton("Ok", null)
                .show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askForPermission(permission: String) {
        oldWayToRequestPermissions(permission)
        //requestPermissionLauncher.launch(permission)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun oldWayToRequestPermissions(permission: String) {
        requestPermissions(arrayOf(permission), LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location permission was granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Location permission was not granted", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    /*
   val requestPermissionLauncher =
           registerForActivityResult(
                   ActivityResultContracts.RequestPermission()
           ) { isGranted: Boolean ->
               if (isGranted) {
                   startCameraActivity()
               } else {
                   failedGracefully()
               }
           }
   */

    enum class CheckPermissionResult {
        GRANTED,
        DENIED,
        NEED_TO_REQUEST,
        NEED_TO_EXPLAIN
    }
}