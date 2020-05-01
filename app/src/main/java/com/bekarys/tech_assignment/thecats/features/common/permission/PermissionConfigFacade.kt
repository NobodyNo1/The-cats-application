package com.bekarys.tech_assignment.thecats.features.common.permission

import android.Manifest

object PermissionConfigFacade {

    fun getPermissionConfig(permission: PermissionDefinedPermissions): PermissionConfig {
        return when (permission) {
            PermissionDefinedPermissions.STORAGE -> createPermissionConfig(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                "external storage",
                "open gallery to choose your images"
            )
            else -> throw IllegalStateException("Unexpected value: $permission")
        }
    }

    private fun createPermissionConfig(
        permission: String,
        permissionName: String,
        actionDescription: String
    ): PermissionConfig {
        return createPermissionConfig(
            listOf(permission),
            permissionName,
            actionDescription
        )
    }

    private fun createPermissionConfig(
        permission: List<String>,
        permissionName: String,
        actionDescription: String
    ): PermissionConfig {
        return PermissionConfig(
            permission,
            "Without this permission this app is unable to " +
                    actionDescription
                    + ". Are you sure you want to deny this permission.",
            "Now you must allow $permissionName access from settings."
        )
    }
}