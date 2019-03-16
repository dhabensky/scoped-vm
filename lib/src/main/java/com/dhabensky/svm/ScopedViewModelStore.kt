package com.dhabensky.svm

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import java.util.HashMap

class ScopedViewModelStore {

    internal class Scope {
        val requesters = mutableSetOf<Fragment>()
        val store = ViewModelStore()
    }


    private val scopeMap = HashMap<String, Scope>()
    private val allRequesters = mutableSetOf<Fragment>()
    private val allObservers = mutableSetOf<FragmentOnDestroyListener>()

    fun put(key: String, viewModel: ViewModel, scope: String, requester: Fragment) {
        var entry = scopeMap[scope]
        if (entry == null) {
            entry = Scope()
            scopeMap[scope] = entry
        }
        if (getViewModel(key, entry.store) !== viewModel) {
            putViewModel(key, viewModel, entry.store)
        }

        entry.requesters.add(requester)
        if (!allRequesters.contains(requester)) {
            allRequesters.add(requester)
            val observer = FragmentOnDestroyListener(requester)
            observer.attach()
        }
    }

    fun get(key: String, scope: String): ViewModel? {
        val store = scopeMap[scope]?.store ?: return null
        return getViewModel(key, store)
    }

    fun removeRequester(requester: Fragment) {
        val toRemove = arrayListOf<String>()
        for ((scopeName, scope) in scopeMap.entries) {
            val removed = scope.requesters.remove(requester)
            if (removed && scope.requesters.isEmpty()) {
                toRemove.add(scopeName)
            }
        }
        for (s in toRemove) {
            println("scope $s cleared")
            val scope = scopeMap.remove(s)
            scope?.store?.clear()
        }
        allRequesters.remove(requester)
    }

    fun softRemoveRequester(requester: Fragment) {
        for (scope in scopeMap.values) {
            scope.requesters.remove(requester)
        }
        allRequesters.remove(requester)
    }

    fun clear() {
        for (o in allObservers) {
            o.detach()
        }
        allObservers.clear()
        allRequesters.clear()
        for ((name, scope) in scopeMap.entries) {
            println("scope $name cleared")
            scope.store.clear()
        }
        scopeMap.clear()
    }

    fun clearEmpty() {
        val toRemove = arrayListOf<String>()

        for ((name, scope) in scopeMap.entries) {
            if (scope.requesters.isEmpty()) {
                toRemove.add(name)
            }
        }

        for (s in toRemove) {
            val scope = scopeMap.remove(s)
            if (scope != null) {
                println("scope $s cleared")
                scope.store.clear()
            }
        }
    }

    inner class FragmentOnDestroyListener(
        private val fragment: Fragment
    ): LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        internal fun onDestroyFragment() {
            val activity = fragment.activity
            val isChangingConfiguration = activity != null && activity.isChangingConfigurations
            when {
                isChangingConfiguration -> onTemporaryDestroy()
                else                    -> onDestroy()
            }
        }

        private var attached = false

        fun attach() {
            if (!attached) {
                attached = true
                fragment.lifecycle.addObserver(this)
            }
        }

        fun detach() {
            if (attached) {
                attached = false
                fragment.lifecycle.removeObserver(this)
            }
        }

        fun onTemporaryDestroy() {
            softRemoveRequester(fragment)
        }

        fun onDestroy() {
            removeRequester(fragment)
        }

    }

}
