package com.dhabensky.svm

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class ScopedViewModelProvider(
    private val factory: ViewModelProvider.Factory,
    private val store: ScopedViewModelStore,
    private val scope: String
) {

    private val DEFAULT_KEY = "androidx.lifecycle.ViewModelProvider.DefaultKey"

    @MainThread
    fun <T : ViewModel> get(modelClass: Class<T>, requester: Fragment): T {
        val canonicalName = modelClass.canonicalName
            ?: throw IllegalArgumentException("Local and anonymous classes can not be ViewModels")
        return get("$DEFAULT_KEY:$canonicalName", modelClass, requester)
    }

    @Suppress("UNCHECKED_CAST")
    @MainThread
    fun <T : ViewModel> get(key: String, modelClass: Class<T>, requester: Fragment): T {
        if (requester.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            throw IllegalArgumentException("Cannot create ViewModel for destroyed requester")
        }

        var viewModel: ViewModel? = store.get(key, scope)

        if (modelClass.isInstance(viewModel)) {
            // nothing to do
        } else {
            if (viewModel != null) {
                // TODO: log a warning.
            }
            viewModel = null
        }

        if (viewModel == null) {
            viewModel = factory.create(modelClass)
        }
        store.put(key, viewModel, scope, requester)

        return viewModel as T
    }

}
