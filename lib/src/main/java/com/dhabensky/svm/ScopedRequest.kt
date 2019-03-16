package com.dhabensky.svm

import androidx.lifecycle.Lifecycle

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class ScopedRequest(
    val requester: Lifecycle,
    val scope: String
)
