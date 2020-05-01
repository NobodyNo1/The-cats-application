package com.bekarys.tech_assignment.thecats.features.common.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

class PermissionManager(private val permissionSessionManager: PermissionSessionManager) {

    fun shouldAskPermission(
        context: Activity?,
        permissions: Array<String?>
    ): Boolean {
        if (!shouldAskPermission()) return false
        for (permission in permissions) {
            val permissionResult = ActivityCompat.checkSelfPermission(context!!, permission!!)
            if (permissionResult != PackageManager.PERMISSION_GRANTED) return true
        }
        return false
    }

    fun checkPermission(
        activity: Activity,
        permissionConfig: PermissionConfig,
        listener: PermissionAskListener
    ) {
        if (!shouldAskPermission(activity, permissionConfig.permission.toTypedArray())) {
            listener.onPermissionGranted(permissionConfig)
            return
        }
        for (permission in permissionConfig.permission) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                listener.onPermissionPreviouslyDenied(permissionConfig)
                return
            }
            if (permissionSessionManager.isFirstTimeAsking(permission)) {
                permissionSessionManager.firstTimeAsking(permission, false)
                listener.onNeedPermission(permissionConfig)
                return
            }
            listener.onPermissionPreviouslyDeniedWithNeverAskAgain(permissionConfig)
        }
    }

    private fun shouldAskPermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

}