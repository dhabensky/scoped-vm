package com.dhabensky.svm.subscription

import androidx.lifecycle.*

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
object VMSubscriptions {

    fun owner(vmStoreOwner: ViewModelStoreOwner): ViewModelOwnerSubscriptions {
        return VMSubscriptions.getOrNewInstance(
            ViewModelOwnerSubscriptions::class.java,
            vmStoreOwner.viewModelStore
        )
    }

    fun user(vmStoreOwner: ViewModelStoreOwner): ViewModelUserSubscriptions {
        return VMSubscriptions.getOrNewInstance(
            ViewModelUserSubscriptions::class.java,
            vmStoreOwner.viewModelStore
        )
    }

    private fun <T : ViewModel> getOrNewInstance(modelClass: Class<T>, store: ViewModelStore): T {
        return getOrNewInstance(defaultKey(modelClass), modelClass, store)
    }

    private fun <T : ViewModel> getOrNewInstance(key: String, modelClass: Class<T>, store: ViewModelStore): T {

        var viewModel = getViewModel(key, store)

        if (modelClass.isInstance(viewModel)) {
            return viewModel as T
        }
        else {
            if (viewModel != null) {
                // TODO: log a warning.
            }
        }

        viewModel = modelClass.newInstance()
        putViewModel(key, viewModel, store)
        return viewModel
    }

}
