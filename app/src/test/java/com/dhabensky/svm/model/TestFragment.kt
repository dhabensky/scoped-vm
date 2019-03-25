package com.dhabensky.svm.model

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dhabensky.svm.ScopedViewModelProviders

/**
 * @author dhabensky <dhabensky@yandex.ru>
 */
class TestFragment(
    scope: String?
): Fragment() {

    private var scope: String? = null

    init {
        arguments = Bundle().apply {
            putString("scope", scope)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope = arguments?.getString("scope")

        ScopedViewModelProviders.forScope(this, scope)
            .of(activity!!)
            .get(SpyableViewModel::class.java)
    }

}
