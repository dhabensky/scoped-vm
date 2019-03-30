package com.dhabensky.scopedvm.test.app

import android.os.Bundle
import android.view.View
import com.dhabensky.scopedvm.test.ScopedFragment

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class MainFragment : ScopedFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener {
            replaceFragment(SearchFragment())
        }
    }

}
