package com.dhabensky.svm.model

import androidx.lifecycle.ViewModel
import org.mockito.Mockito.mock

/**
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
