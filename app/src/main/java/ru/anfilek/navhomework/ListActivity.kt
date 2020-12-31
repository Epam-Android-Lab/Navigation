package ru.anfilek.navhomework

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.anfilek.navhomework.databinding.ActivityListBinding
import kotlin.random.Random
import kotlin.random.nextInt

class ListActivity : AppCompatActivity(), IButtonRetryListener {
    private var cameraId = R.id.my_camera

    private val binding: ActivityListBinding by lazy {
        val tmpBinding = ActivityListBinding.inflate(layoutInflater)
        setContentView(tmpBinding.root)
        tmpBinding
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.fabStartCamera.setOnClickListener {
            startCameraFeature()
        }

        binding.buttonItem.setOnClickListener {
            startItemActivity()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.my_camera -> {
                    cameraId = R.id.my_camera
                }
                R.id.custom_camera -> {
                    cameraId = R.id.custom_camera
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun startCameraFeature() {
        // check camera permission
        // handle the check result
        // show dialog if it is needed
        // feel free to customise the button if it is needed
        check()
        //startCameraActivity(usingCamera = cameraId)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun startCameraActivity(usingCamera: Int) {
        when (usingCamera) {
            R.id.my_camera -> {
                startActivity(Intent(this, CameraActivity::class.java))
            }
            R.id.custom_camera -> {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val chooser = Intent.createChooser(intent, getString(R.string.chooser_title))
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(chooser)
                } else {
                    Toast.makeText(this, "No cameras found", Toast.LENGTH_SHORT).show()
                }
            }
        }

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
                startCameraActivity(usingCamera = cameraId)
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

    private fun showExplanation(withRationale: Boolean = false) {
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
                    startCameraActivity(usingCamera = cameraId)
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