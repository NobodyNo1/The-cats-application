package com.bekarys.tech_assignment.thecats.features.breedlist.presentation.view

import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData

object EmptyBreedListView : BreedListView {

    override fun onError(message: String) = Unit

    override fun onInitialDataLoaded(data: List<BreedData>) = Unit

    override fun onNextPageLoaded(data: List<BreedData>) = Unit

    override fun onLoading(isActive: Boolean) = Unit
}