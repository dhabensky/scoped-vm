package com.dhabensky.svm

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.dhabensky.svm.subscription.VMSubscriptions

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */

class ScopedViewModelProviders
private constructor(
    private val requester: Fragment,
    private val scope: String
) {

    companion object {
        @JvmStatic
        fun forScope(requester: Fragment, scope: String): ScopedViewModelProviders {
            return ScopedViewModelProviders(requester, scope)
        }
    }

    fun of(activity: FragmentActivity, factory: ViewModelProvider.Factory? = null)
            : ScopedViewModelProvider {

        val ownerSubs = VMSubscriptions.owner(activity)

        val fact = factory ?: ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application)

        return ScopedViewModelProvider(fact, ownerSubs, scope, requester)
    }

    fun of(fragment: Fragment, factory: ViewModelProvider.Factory? = null)
            : ScopedViewModelProvider {

        val ownerSubs = VMSubscriptions.owner(fragment)

        val fact = factory ?: ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.activity!!.application)

        return ScopedViewModelProvider(fact, ownerSubs, scope, requester)
    }

}
