package com.dhabensky.svm

import androidx.lifecycle.*
import java.util.HashMap

class ScopedViewModelStore {

    internal class Scope {
        val requesters = mutableSetOf<Lifecycle>()
        val store = ViewModelStore()
    }


    private val scopeMap = HashMap<String, Scope>()
    private val allRequesters = mutableSetOf<Lifecycle>()

    fun put(key: String, viewModel: ViewModel, scopedRequest: ScopedRequest) {
        var entry = scopeMap[scopedRequest.scope]
        if (entry == null) {
            entry = Scope()
            scopeMap[scopedRequest.scope] = entry
        }
        putViewModel(key, viewModel, entry.store)

        val requester = scopedRequest.requester
        entry.requesters.add(requester)
        if (!allRequesters.contains(requester)) {
            requester.addObserver(OnDestroyLifecycleObserver { removeRequester(requester) })
            entry.requesters.add(requester)
        }
    }

    fun get(key: String, scope: String): ViewModel? {
        val store = scopeMap[scope]?.store ?: return null
        return getViewModel(key, store)
    }

    fun removeRequester(requester: Any) {
        val toRemove = arrayListOf<String>()
        for ((scopeName, scope) in scopeMap.entries) {
            val removed = scope.requesters.remove(requester)
            if (removed && scope.requesters.isEmpty()) {
                toRemove.add(scopeName)
            }
        }
        for (s in toRemove) {
            val scope = scopeMap.remove(s)
            scope?.store?.clear()
        }
    }

}
