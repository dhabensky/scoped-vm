package com.dhabensky.svm

import androidx.annotation.MainThread
import androidx.lifecycle.*

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class ScopedViewModelProvider(
    private val factory: ViewModelProvider.Factory,
    private val store: ScopedViewModelStore
) {

    private val DEFAULT_KEY = "androidx.lifecycle.ViewModelProvider.DefaultKey"

    @MainThread
    fun <T : ViewModel> get(modelClass: Class<T>, scopedRequest: ScopedRequest): T {
        val canonicalName = modelClass.canonicalName
            ?: throw IllegalArgumentException("Local and anonymous classes can not be ViewModels")
        return get("$DEFAULT_KEY:$canonicalName", modelClass, scopedRequest)
    }

    @Suppress("UNCHECKED_CAST")
    @MainThread
    fun <T : ViewModel> get(key: String, modelClass: Class<T>, scopedRequest: ScopedRequest): T {
        if (scopedRequest.requester.currentState == Lifecycle.State.DESTROYED) {
            throw IllegalArgumentException("Cannot create ViewModel for destroyed requester")
        }

        var viewModel: ViewModel? = store.get(key, scopedRequest.scope)

        if (modelClass.isInstance(viewModel)) {
            return viewModel as T
        } else {
            if (viewModel != null) {
                // TODO: log a warning.
            }
        }

        viewModel = factory.create(modelClass)
        store.put(key, viewModel, scopedRequest)

        return viewModel
    }

}
