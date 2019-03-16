package com.dhabensky.svm

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.dhabensky.svm.subscription.VMSubscriptions

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
object ScopedViewModelProviders {

    @JvmStatic
    fun of(scope: String, activity: FragmentActivity, factory: ViewModelProvider.Factory? = null)
            : ScopedViewModelProvider {

        val ownerSubs = VMSubscriptions.owner(activity)

        val fact = factory ?: ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application)

        return ScopedViewModelProvider(fact, ownerSubs, scope)
    }

    @JvmStatic
    fun of(scope: String, fragment: Fragment, factory: ViewModelProvider.Factory? = null)
            : ScopedViewModelProvider {

        val ownerSubs = VMSubscriptions.owner(fragment)

        val fact = factory ?: ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.activity!!.application)

        return ScopedViewModelProvider(fact, ownerSubs, scope)
    }

}
