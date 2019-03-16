package com.dhabensky.svm.test.app

import android.os.Bundle
import android.view.View
import com.dhabensky.svm.test.ScopedFragment
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class SearchFragment : ScopedFragment("Search") {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.data = SimpleDateFormat("HH:mm:ss").format(Date())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener {
            replaceFragment(ListFragment())
        }
    }

}
