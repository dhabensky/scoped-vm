package com.dhabensky.svm.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * Created on 24.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class FragmentHelper(
    activity: FragmentActivity,
    private val containerId: Int
) {

    val transaction = activity.supportFragmentManager.beginTransaction()

    fun add(frag: Fragment): FragmentHelper {
        transaction.add(containerId, frag)
        return this
    }

    fun remove(frag: Fragment): FragmentHelper {
        transaction.remove(frag)
        return this
    }

    fun replace(frag: Fragment): FragmentHelper {
        transaction.replace(containerId, frag)
        return this
    }

    fun addToBackStack(): FragmentHelper {
        transaction.addToBackStack(null)
        return this
    }

}