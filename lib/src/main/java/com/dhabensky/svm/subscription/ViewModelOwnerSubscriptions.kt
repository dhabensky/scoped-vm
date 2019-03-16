package com.dhabensky.svm.subscription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.getViewModel
import androidx.lifecycle.putViewModel
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


    fun get(key: String, scope: String): ViewModel? {
        val store = scopeMap[scope]?.store ?: return null
        return getViewModel(key, store)
    }

    fun put(key: String, viewModel: ViewModel, scopeName: String) {
        var scope = scopeMap[scopeName]
        if (scope == null) {
            scope = Scope()
            scopeMap[scopeName] = scope
        }
        putViewModel(key, viewModel, scope.store)
    }


    fun add(subscription: VMSubscription) {
        val name = subscription.scope
        var scope = scopeMap[name]
        if (scope == null) {
            scope = Scope()
            scopeMap[name] = scope
        }
        scope.subscriptions.add(subscription)
    }

    fun remove(subscription: VMSubscription) {
        val name = subscription.scope
        val scope = scopeMap[name]
        if (scope != null) {
            scope.subscriptions.remove(subscription)
            if (scope.subscriptions.isEmpty()) {
                println("scope $name cleared")
                scope.store.clear()
                scopeMap.remove(name)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        for ((name, scope) in scopeMap.entries) {
            println("scope $name cleared")
            scope.store.clear()
        }
        scopeMap.clear()
    }

}
