package com.dhabensky.svm

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.dhabensky.svm.subscription.VMSubscription
import com.dhabensky.svm.subscription.VMSubscriptions

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class ScopedViewModelProvider(
    private val storeOwner: ViewModelStoreOwner,
    private val factory: ViewModelProvider.Factory,
    private val scope: String?,
    private val requester: Fragment
) {

    @MainThread
    fun <T : ViewModel> get(modelClass: Class<T>): T {
        return get(defaultKey(modelClass), modelClass)
    }

    @MainThread
    fun <T : ViewModel> get(key: String, modelClass: Class<T>): T {
        return provider.get(key, modelClass)
    }

    private val provider by lazy {
        ViewModelProvider(getScopedStore(), factory)
    }

    private fun getScopedStore(): ViewModelStore {
        if (scope != null) {
            val owner = VMSubscriptions.owner(storeOwner)
            // subscriptions
            val subscription = VMSubscription(owner, scope)
            owner.addSubscription(subscription)
            VMSubscriptions.user(requester).addSubscription(subscription)

            return owner.getScopedStore(scope)
        }
        else {
            return storeOwner.viewModelStore
        }
    }

}
