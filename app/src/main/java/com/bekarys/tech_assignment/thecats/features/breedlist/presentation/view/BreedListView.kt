package com.bekarys.tech_assignment.thecats.features.breedlist.presentation.view

import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData
import com.bekarys.tech_assignment.thecats.features.common.base.BaseView

interface BreedListView : BaseView {

    fun onInitialDataLoaded(data: List<BreedData>)

    fun onNextPageLoaded(data: List<BreedData>)

    fun onLoading(isActive: Boolean)
}