package com.dhabensky.svm

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class ScopedViewModelProviders
private constructor(
    private val requester: Fragment,
    private val scope: String?
) {

    companion object {
        @JvmStatic
        fun forScope(requester: Fragment, scope: String?): ScopedViewModelProviders {
            if (requester.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                throw IllegalArgumentException("Cannot create ScopedViewModelProvider for destroyed requester")
            }
            return ScopedViewModelProviders(requester, scope)
        }
    }

    fun of(activity: FragmentActivity, factory: ViewModelProvider.Factory? = null)
            : ScopedViewModelProvider {

        val fact = factory ?: ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application)

        return ScopedViewModelProvider(activity, fact, scope, requester)
    }

    fun of(fragment: Fragment, factory: ViewModelProvider.Factory? = null)
            : ScopedViewModelProvider {

        val fact = factory ?: ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.activity!!.application)

        return ScopedViewModelProvider(fragment, fact, scope, requester)
    }

}
