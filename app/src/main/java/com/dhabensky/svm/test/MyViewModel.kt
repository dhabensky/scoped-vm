package com.dhabensky.svm.test

import androidx.lifecycle.ViewModel

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class MyViewModel : ViewModel() {

    var scope: String? = null
    var data: String? = null

    override fun onCleared() {
        super.onCleared()
        println("vm {scope: $scope, data: $data} cleared")
    }

}
