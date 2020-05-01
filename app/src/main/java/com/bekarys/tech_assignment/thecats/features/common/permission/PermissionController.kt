package com.bekarys.tech_assignment.thecats.features.common.permission

interface PermissionController {
    fun askPermission(
        config: PermissionConfig,
        listener: PermissionGrantedListener
    )
}