package com.bekarys.tech_assignment.thecats.features.common.exceptions.customexceptions

import com.bekarys.tech_assignment.thecats.features.common.exceptions.InternalException

class NoStoredData(message: String) : InternalException(message)