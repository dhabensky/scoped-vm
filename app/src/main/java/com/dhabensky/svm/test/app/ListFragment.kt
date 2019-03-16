package com.dhabensky.svm.test.app

import android.os.Bundle
import android.view.View
import com.dhabensky.svm.test.ScopedFragment

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class ListFragment : ScopedFragment("Search") {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener {
            viewModelFor("detail").data = "set from list"
            replaceFragment(DetailFragment("temp_data"))
        }
    }

}
