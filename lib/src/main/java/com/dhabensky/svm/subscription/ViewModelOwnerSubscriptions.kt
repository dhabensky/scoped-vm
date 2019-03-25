package com.dhabensky.svm.subscription

import androidx.lifecycle.*
import java.util.*

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class ViewModelOwnerSubscriptions : ViewModel() {

    private class Scope {
        val subscriptions = mutableSetOf<VMSubscription>()
        val store = ViewModelStore()
    }

    private val scopeMap = HashMap<String, Scope>()

    fun getScopedStore(scopeName: String): ViewModelStore {
        return getOrCreateScope(scopeName).store
    }

    fun addSubscription(subscription: VMSubscription) {
        val scope = getOrCreateScope(subscription.scope)
        scope.subscriptions.add(subscription)
    }

    fun removeSubscription(subscription: VMSubscription) {
        val name = subscription.scope
        val scope = scopeMap[name]
        if (scope != null) {
            scope.subscriptions.remove(subscription)
            if (scope.subscriptions.isEmpty()) {
                scope.store.clear()
                scopeMap.remove(name)
            }
        }
    }

    private fun getOrCreateScope(scopeName: String): Scope {
        var scope = scopeMap[scopeName]
        if (scope == null) {
            scope = Scope()
            scopeMap[scopeName] = scope
        }
        return scope
    }

    override fun onCleared() {
        super.onCleared()
        for (scope in scopeMap.values) {
            scope.store.clear()
        }
        scopeMap.clear()
    }

}
