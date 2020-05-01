package com.bekarys.tech_assignment.thecats.features.common.data

private const val EMPTY_MESSAGE = ""

sealed class ResponseData<T> {

    data class Success<T>(
        val data: T
    ) : ResponseData<T>()

    data class Fail<T>(
        val exception: Throwable,
        val message: String = EMPTY_MESSAGE
    ) : ResponseData<T>()
}