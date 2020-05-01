package com.bekarys.tech_assignment.thecats.features.common.permission

data class PermissionConfig(
    val permission: List<String>,
    val explanationPermissionMessage: String,
    val explanationSettingNavigationMessage: String
)