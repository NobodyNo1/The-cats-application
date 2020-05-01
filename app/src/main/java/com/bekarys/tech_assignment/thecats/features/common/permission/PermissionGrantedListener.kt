package com.bekarys.tech_assignment.thecats.features.common.permission

interface PermissionGrantedListener {
    fun onPermissionGranted(permissionConfig: PermissionConfig?)
}