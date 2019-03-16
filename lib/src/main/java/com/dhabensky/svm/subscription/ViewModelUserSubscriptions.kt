package com.dhabensky.svm.subscription

import androidx.lifecycle.ViewModel
import java.util.*

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class ViewModelUserSubscriptions : ViewModel() {

    private val subscriptions = LinkedList<VMSubscription>()

    fun add(subscription: VMSubscription) {
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        for (sub in subscriptions) {
            sub.wmOwner.remove(sub)
        }
    }

}
