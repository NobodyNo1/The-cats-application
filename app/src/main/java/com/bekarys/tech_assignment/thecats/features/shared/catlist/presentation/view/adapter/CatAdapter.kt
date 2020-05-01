package com.bekarys.tech_assignment.thecats.features.shared.catlist.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.bekarys.tech_assignment.thecats.databinding.ItemCatBinding
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData

class CatAdapter(
    private val onItemClick: (breedData: BreedData) -> Unit,
    private val onFavoriteClick: (view: CheckBox, breed: BreedData) -> Unit
) : RecyclerView.Adapter<CatViewHolder>() {

    private val listItems: MutableList<BreedData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemCatBinding.inflate(inflater, parent, false)

        return CatViewHolder(
            viewBinding,
            onItemClick,
            onFavoriteClick
        )
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    fun updateData(dataList: List<BreedData>) {
        this.listItems.clear()
        this.listItems.addAll(dataList)
        notifyDataSetChanged()
    }

    fun appendData(data: List<BreedData>) {
        this.listItems.addAll(data)
        notifyDataSetChanged()
    }
}