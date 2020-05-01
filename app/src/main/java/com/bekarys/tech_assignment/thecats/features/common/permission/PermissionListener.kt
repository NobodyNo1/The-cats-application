package com.bekarys.tech_assignment.thecats.features.common.permission

interface PermissionListener {

    fun onPermissionGranted(permissionConfig: PermissionConfig)

    fun onShowExplanationDialog(permissionConfig: PermissionConfig)

    fun onShowNavigateToSettingsDialog(permissionConfig: PermissionConfig)
}