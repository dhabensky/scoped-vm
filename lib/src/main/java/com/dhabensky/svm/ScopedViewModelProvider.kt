package com.dhabensky.svm

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.dhabensky.svm.subscription.VMSubscription
import com.dhabensky.svm.subscription.VMSubscriptions
import com.dhabensky.svm.subscription.ViewModelOwnerSubscriptions

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class ScopedViewModelProvider(
    private val factory: ViewModelProvider.Factory,
    private val store: ViewModelOwnerSubscriptions,
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

        // subscriptions first
        val subscription = VMSubscription(store, scope)
        store.add(subscription)
        VMSubscriptions.user(requester).add(subscription)

        var viewModel: ViewModel? = store.get(key, scope)

        if (modelClass.isInstance(viewModel)) {
            return viewModel as T
        }
        else {
            if (viewModel != null) {
                // TODO: log a warning.
            }
        }

        viewModel = factory.create(modelClass)
        store.put(key, viewModel, scope)

        return viewModel
    }

}
