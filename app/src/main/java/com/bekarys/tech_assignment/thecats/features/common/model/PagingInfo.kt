package com.bekarys.tech_assignment.thecats.features.common.model

private const val EMPTY = ""

data class PagingInfo(
    val isBottomReached: Boolean = false,
    val currentPage: Int = 0
)