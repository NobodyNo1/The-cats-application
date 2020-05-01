package com.bekarys.tech_assignment.thecats.features.common.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bekarys.tech_assignment.thecats.R
import javax.inject.Inject

class PermissionHelper(
    private val context: Context,
    private val permissionManager: PermissionManager
) : PermissionAskListener {

    lateinit var activity: Activity
    var permissionListener: PermissionListener? = null
    lateinit var permissionConfig: PermissionConfig

    fun initialize(activity: Activity) {
        this.activity = activity
    }

    fun makeAction(
        permissionConfig: PermissionConfig,
        permissionListener: PermissionListener
    ) {
        this.permissionListener = permissionListener
        this.permissionConfig = permissionConfig
        if (!permissionManager.shouldAskPermission(
                activity,
                permissionConfig.permission.toTypedArray()
            )
        ) {
            permissionListener.onPermissionGranted(permissionConfig)
            return
        }
        permissionManager.checkPermission(activity, permissionConfig, this)
    }

    override fun onNeedPermission(permissionConfig: PermissionConfig) {
        askForPermission(permissionConfig)
    }

    override fun onPermissionPreviouslyDenied(permissionConfig: PermissionConfig) {
        permissionListener?.onShowExplanationDialog(permissionConfig)
    }

    override fun onPermissionPreviouslyDeniedWithNeverAskAgain(permissionConfig: PermissionConfig) {
        permissionListener?.onShowNavigateToSettingsDialog(permissionConfig)
    }

    override fun onPermissionGranted(permissionConfig: PermissionConfig) {
        if (permissionListener == null) return
        permissionListener!!.onPermissionGranted(permissionConfig)
    }

    fun onPermissionResult(requestCode: Int, isPermissionGranted: Boolean) {
        if (permissionListener == null) return
        check(requestCode == PERMISSION_REQUEST_CODE) { "Permission was requested outside" }
        if (isPermissionGranted) permissionListener!!.onPermissionGranted(permissionConfig) else Toast.makeText(
            context,
            "Permission denied",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun askForPermission(permissionConfig: PermissionConfig) {
        ActivityCompat.requestPermissions(
            activity,
            permissionConfig.permission.toTypedArray(),
            PERMISSION_REQUEST_CODE
        )
    }

    fun goToSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.parse("package:" + activity.packageName)
        intent.data = uri
        activity.startActivity(intent)
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 368
        private const val EXPLANATION_DIALOG_REQUEST_CODE = 124
    }

}