package com.dhabensky.svm

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
object ScopedViewModelProviders {

    @JvmStatic
    fun of(scope: String, activity: FragmentActivity, factory: ViewModelProvider.Factory? = null)
            : ScopedViewModelProvider {

        val scopedStoreHolder = ViewModelProviders.of(activity, ViewModelProvider.NewInstanceFactory())
            .get(ScopedStoreHolder::class.java)

        val fact = factory ?: ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application)

        return ScopedViewModelProvider(fact, scopedStoreHolder.scopedStore, scope)
    }

    @JvmStatic
    fun of(scope: String, fragment: Fragment, factory: ViewModelProvider.Factory? = null)
            : ScopedViewModelProvider {

        val scopedStoreHolder = ViewModelProviders.of(fragment, ViewModelProvider.NewInstanceFactory())
            .get(ScopedStoreHolder::class.java)
        scopedStoreHolder.attach(fragment.lifecycle)

        val fact = factory ?: ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.activity!!.application)

        return ScopedViewModelProvider(fact, scopedStoreHolder.scopedStore, scope)
    }

    /**
     * Instance obtained and stored in [ViewModelStoreOwner.getViewModelStore]
     * (used existing mechanism to survive configuration change).
     */
    internal class ScopedStoreHolder: ViewModel(), LifecycleObserver {
        val scopedStore = ScopedViewModelStore()
        private var lifecycle: Lifecycle? = null

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onParentStart() {
            scopedStore.clearEmpty()
        }

        override fun onCleared() {
            super.onCleared()
            scopedStore.clear()
        }

        fun attach(lifecycle: Lifecycle) {
            this.lifecycle = lifecycle
            lifecycle.addObserver(this)
        }

    }

}
