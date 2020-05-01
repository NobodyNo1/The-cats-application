package com.bekarys.tech_assignment.thecats.features.common.exceptions

abstract class NetworkException(
    message: String
) : InternalException(message)