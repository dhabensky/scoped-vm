package com.dhabensky.scopedvm.model

import androidx.lifecycle.ViewModel

/**
 * @author dhabensky <dhabensky@yandex.ru>
 */
class TestViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        clearedTimes++
    }

    var clearedTimes = 0
        private set

}
