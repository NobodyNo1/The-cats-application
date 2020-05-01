package com.bekarys.tech_assignment.thecats.features.favorites.presentation.view

import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData

object EmptyFavoritesView :
    FavoritesView {

    override fun onError(message: String) = Unit

    override fun onFavoriteDataLoaded(data: List<BreedData>) = Unit

    override fun onLoading(isActive: Boolean) = Unit
}