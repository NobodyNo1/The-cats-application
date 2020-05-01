package com.bekarys.tech_assignment.thecats.features.common.exceptions.customexceptions

import com.bekarys.tech_assignment.thecats.features.common.exceptions.NetworkException

sealed class ConnectionException(
    message: String
) : NetworkException(message) {

    object NoConnection : ConnectionException("No connection")
}