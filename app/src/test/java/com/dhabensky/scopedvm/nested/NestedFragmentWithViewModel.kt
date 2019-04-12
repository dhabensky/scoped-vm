package com.dhabensky.scopedvm.nested

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

/**
 * @author dhabensky <dhabensky@yandex.ru>
 */
class NestedFragmentWithViewModel : Fragment() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		println(" - NestedFragmentWithViewModel created")
		ViewModelProviders.of(this).get(NestedFragmentViewModel::class.java)
		ViewModelProviders.of(activity!!).get(NestedActivityViewModel::class.java)
	}

	override fun onDestroy() {
		println(" - NestedFragmentWithViewModel destroyed")
		super.onDestroy()
	}

	class NestedFragmentViewModel : ViewModel() {

		init {
			println(" - ViewModel of fragment created")
		}

		override fun onCleared() {
			super.onCleared()
			println(" - ViewModel of fragment onCleared")
		}
	}

	class NestedActivityViewModel : ViewModel() {

		init {
			println(" - ViewModel of activity created")
		}

		override fun onCleared() {
			super.onCleared()
			println(" - ViewModel of activity onCleared")
		}
	}

}
