package com.bekarys.tech_assignment.thecats.features.breedlist.data.model.network

import com.google.gson.annotations.SerializedName

private const val EMPTY_IMPERIAL = ""
private const val EMPTY_METRIC = ""

data class WeightModel(
    @SerializedName("imperial")
    var imperial: String = EMPTY_IMPERIAL,
    @SerializedName("metric")
    var metric: String = EMPTY_METRIC
)