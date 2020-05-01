package com.bekarys.tech_assignment.thecats.features.breedlist.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import com.bekarys.tech_assignment.thecats.R
import com.bekarys.tech_assignment.thecats.application.ApplicationComponentHolder
import com.bekarys.tech_assignment.thecats.databinding.FragmentCatListBinding
import com.bekarys.tech_assignment.thecats.di.catlist.DaggerCatListComponent
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData
import com.bekarys.tech_assignment.thecats.features.breedlist.presentation.presenter.BreedListPresenter
import com.bekarys.tech_assignment.thecats.features.common.base.BaseFragment
import com.bekarys.tech_assignment.thecats.features.shared.catlist.presentation.view.adapter.CatAdapter
import com.bekarys.tech_assignment.thecats.features.common.extensions.alert
import com.bekarys.tech_assignment.thecats.features.common.extensions.toggleVisibility
import com.bekarys.tech_assignment.thecats.features.common.pagination.PaginationListener
import com.bekarys.tech_assignment.thecats.features.common.permission.*

class BreedListFragment :
    BaseFragment<FragmentCatListBinding, BreedListView, BreedListPresenter>(R.layout.fragment_cat_list),
    BreedListView {

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

    private lateinit var paginationListener: PaginationListener

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        initView()
        presenter.loadInitialPage()
    }

    override fun bindView(view: View): FragmentCatListBinding = FragmentCatListBinding.bind(view)

    override fun injectFields() {
        DaggerCatListComponent.builder().appComponent(
            ApplicationComponentHolder.component
        ).build().inject(this)
    }

    override fun onInitialDataLoaded(data: List<BreedData>) {
        binding.refreshList.isRefreshing = false
        adapter.updateData(data)
    }

    override fun onNextPageLoaded(data: List<BreedData>) {
        adapter.appendData(data)
    }

    override fun onLoading(isActive: Boolean) {
        binding.loadingProgress.toggleVisibility(isActive)
    }

    private fun showDownloadImageDialog(data: BreedData) {
        alert("Download Image", "Do you want to download image?") {
            askPermission(PermissionConfigFacade.getPermissionConfig(PermissionDefinedPermissions.STORAGE)) {
                presenter.downloadImage(data)
            }
        }
    }

    private fun initView() {
        setupRecyclerView()
        setupSwipeToRefresh()
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        paginationListener = PaginationListener(linearLayoutManager) {
            presenter.loadNextPage()
        }

        binding.catsRv.adapter = adapter
        binding.catsRv.layoutManager =
            linearLayoutManager
        binding.catsRv.addOnScrollListener(paginationListener)
    }

    private fun setupSwipeToRefresh() {
        binding.refreshList.setOnRefreshListener {
            presenter.loadInitialPage()
            binding.refreshList.isRefreshing = true
        }
    }
}