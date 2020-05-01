package com.bekarys.tech_assignment.thecats.features.favorites.presentation.view

import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData
import com.bekarys.tech_assignment.thecats.features.common.base.BaseView

interface FavoritesView: BaseView {

    fun onFavoriteDataLoaded(data: List<BreedData>)

    fun onLoading(isActive: Boolean)
}