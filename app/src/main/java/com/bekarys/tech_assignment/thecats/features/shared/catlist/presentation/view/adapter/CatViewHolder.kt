package com.bekarys.tech_assignment.thecats.features.shared.catlist.presentation.view.adapter

import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.bekarys.tech_assignment.thecats.databinding.ItemCatBinding
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData
import com.bekarys.tech_assignment.thecats.features.common.extensions.either
import com.bekarys.tech_assignment.thecats.features.common.extensions.setImage

class CatViewHolder(
    private val binding: ItemCatBinding,
    private val onItemClick: (breedData: BreedData) -> Unit,
    private val onFavoriteClick: (view: CheckBox, breed: BreedData) -> Unit
) : RecyclerView.ViewHolder(binding.itemContainer) {

    fun bind(data: BreedData) {
        binding.itemBreedImage.setImage(data.localImagePath either data.serverImagePath)
        binding.itemBreedName.text = data.name
        binding.itemBreedDescription.text = data.description
        binding.favoriteToggle.setOnClickListener {
            onFavoriteClick(binding.favoriteToggle, data)
        }
        binding.favoriteToggle.isChecked = data.isFavorite
        binding.itemContainer.setOnClickListener {
            onItemClick(data)
        }
    }
}
