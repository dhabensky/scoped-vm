package com.dhabensky.svm

import androidx.lifecycle.ViewModelProvider

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */

object ScopedViewModelProviders {

    @JvmStatic
    fun of(scope: String): ScopedViewModelProvider {
        return ScopedViewModelProvider(ViewModelProvider.NewInstanceFactory(), store)
    }

    @JvmStatic
    private val store = ScopedViewModelStore()

}
