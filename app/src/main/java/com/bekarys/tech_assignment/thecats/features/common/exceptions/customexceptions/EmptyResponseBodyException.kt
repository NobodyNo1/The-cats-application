package com.bekarys.tech_assignment.thecats.features.common.exceptions.customexceptions

import com.bekarys.tech_assignment.thecats.features.common.exceptions.NetworkException

class EmptyResponseBodyException(message: String) : NetworkException(message)