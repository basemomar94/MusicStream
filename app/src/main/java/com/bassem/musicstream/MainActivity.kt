package com.bassem.musicstream

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pub.devrel.easypermissions.EasyPermissions
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
    }

    private fun requestPermission() {
        EasyPermissions.requestPermissions(
            this,
            "Weather now needs to access location",
            101,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun hasPermission() =
        EasyPermissions.hasPermissions(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)

    private fun checkPermission() {
        if (hasPermission()) {

        } else {

            requestPermission()
        }
    }
}