package com.bekarys.tech_assignment.thecats.features.common.permission

interface PermissionAskListener {

    fun onNeedPermission(permissionConfig: PermissionConfig)

    fun onPermissionPreviouslyDenied(permissionConfig: PermissionConfig)

    fun onPermissionPreviouslyDeniedWithNeverAskAgain(permissionConfig: PermissionConfig)

    fun onPermissionGranted(permissionConfig: PermissionConfig)
}