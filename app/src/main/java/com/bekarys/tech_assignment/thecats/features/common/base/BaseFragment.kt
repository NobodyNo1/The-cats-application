package com.bekarys.tech_assignment.thecats.features.common.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bekarys.tech_assignment.thecats.features.common.extensions.toast
import com.bekarys.tech_assignment.thecats.features.common.permission.*
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding, V : BaseView, P : BasePresenter<V>>(
    @LayoutRes layout: Int
) : Fragment(layout), BaseView {

    @Inject
    lateinit var presenter: P
    lateinit var binding: VB

    var delegateAction: () -> Unit = {}
    private val permissionListener: PermissionGrantedListener =
        object : PermissionGrantedListener {
            override fun onPermissionGranted(permissionConfig: PermissionConfig?) {
                delegateAction()
                delegateAction = {}
            }
        }

    @CallSuper
    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectFields()
        presenter.attachView(this as V)
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = bindView(view)
        onFragmentReady(savedInstanceState)
    }

    fun getPermissionController(): PermissionController? = activity as? PermissionController


    abstract fun onFragmentReady(savedInstanceState: Bundle?)

    abstract fun injectFields()

    abstract fun bindView(view: View): VB

    override fun onError(message: String) {
        toast(message)
    }

    @CallSuper
    override fun onDetach() {
        super.onDetach()
        presenter.removeView()
    }

    fun askPermission(permissionConfig: PermissionConfig, delegateAction: () -> Unit) {
        getPermissionController()?.let {
            this.delegateAction = delegateAction
            it.askPermission(
                permissionConfig,
                permissionListener
            )
        }
    }
}
