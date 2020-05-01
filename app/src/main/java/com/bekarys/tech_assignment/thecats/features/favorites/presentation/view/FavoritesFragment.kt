package com.bekarys.tech_assignment.thecats.features.favorites.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import com.bekarys.tech_assignment.thecats.R
import com.bekarys.tech_assignment.thecats.application.ApplicationComponentHolder
import com.bekarys.tech_assignment.thecats.databinding.FragmentFavoritesBinding
import com.bekarys.tech_assignment.thecats.di.favorites.DaggerFavoritesComponent
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData
import com.bekarys.tech_assignment.thecats.features.common.base.BaseFragment
import com.bekarys.tech_assignment.thecats.features.common.extensions.alert
import com.bekarys.tech_assignment.thecats.features.shared.catlist.presentation.view.adapter.CatAdapter
import com.bekarys.tech_assignment.thecats.features.common.extensions.toast
import com.bekarys.tech_assignment.thecats.features.common.extensions.toggleVisibility
import com.bekarys.tech_assignment.thecats.features.common.permission.PermissionConfigFacade
import com.bekarys.tech_assignment.thecats.features.common.permission.PermissionDefinedPermissions
import com.bekarys.tech_assignment.thecats.features.favorites.presentation.presenter.FavoritesPresenter

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesView, FavoritesPresenter>(R.layout.fragment_favorites), FavoritesView {

    private val adapter: CatAdapter = CatAdapter(
        { data ->
            showDownloadImageDialog(data)
        }, { view: CheckBox, data: BreedData ->
            if (data.isFavorite)
                presenter.removeFavorite(data)
            else
                presenter.addFavorite(data)

            data.isFavorite = !data.isFavorite
        }
    )

    private fun showDownloadImageDialog(data: BreedData) {
        alert("Download Image", "Do you want to download image?") {
            askPermission( PermissionConfigFacade.getPermissionConfig(PermissionDefinedPermissions.STORAGE)){
                presenter.downloadImage(data)
            }
        }
    }

    override fun injectFields() {
        DaggerFavoritesComponent.builder().appComponent(
            ApplicationComponentHolder.component
        ).build().inject(this)
    }

    override fun onFavoriteDataLoaded(data: List<BreedData>) {
        binding.refreshList.isRefreshing = false
        adapter.updateData(data)
    }

    override fun onLoading(isActive: Boolean) {
        binding.loadingProgress.toggleVisibility(isActive)
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        initUI()
    }

    override fun bindView(view: View): FragmentFavoritesBinding = FragmentFavoritesBinding.bind(view)

    private fun initUI() {
        setupRecyclerView()
        setupSwipeToRefresh()
    }

    private fun setupRecyclerView() {
        binding.catsRv.adapter = adapter
        binding.catsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        presenter.loadFavorites()
    }

    private fun setupSwipeToRefresh() {
        binding.refreshList.setOnRefreshListener {
            presenter.loadFavorites()
            binding.refreshList.isRefreshing = true
        }
    }
}