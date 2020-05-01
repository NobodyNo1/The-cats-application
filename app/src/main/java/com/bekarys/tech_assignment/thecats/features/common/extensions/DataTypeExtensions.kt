package com.bekarys.tech_assignment.thecats.features.common.extensions

infix fun String.either(
    other: String
) = if (this.isEmpty()) other else this