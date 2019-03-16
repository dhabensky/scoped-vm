package com.dhabensky.svm.subscription

import androidx.lifecycle.*

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
object VMSubscriptions {

    fun owner(vmStoreOwner: ViewModelStoreOwner): ViewModelOwnerSubscriptions {
        val vmProvider = ViewModelProvider(vmStoreOwner, ViewModelProvider.NewInstanceFactory())
        return vmProvider.get(ViewModelOwnerSubscriptions::class.java)
    }

    fun user(vmStoreOwner: ViewModelStoreOwner): ViewModelUserSubscriptions {
        val vmProvider = ViewModelProvider(vmStoreOwner, ViewModelProvider.NewInstanceFactory())
        return vmProvider.get(ViewModelUserSubscriptions::class.java)
    }

}
