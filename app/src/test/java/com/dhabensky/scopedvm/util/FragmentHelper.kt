package com.dhabensky.scopedvm.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * @author dhabensky <dhabensky@yandex.ru>
 */
class FragmentHelper(
		fragmentManager: FragmentManager,
		private val containerId: Int
) {

	val transaction = fragmentManager.beginTransaction()

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

	fun addToBackStack(name: String? = null): FragmentHelper {
		transaction.addToBackStack(name)
		return this
	}

}
