package com.dhabensky.svm.util

import androidx.lifecycle.ViewModel
import org.mockito.Mockito.mock

/**
 * Created on 23.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class SpyableViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        spy.onCleared()
    }

    val spy: OnClearedSpy = mock(OnClearedSpy::class.java)

    interface OnClearedSpy {
        fun onCleared()
    }

}
