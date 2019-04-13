package com.dhabensky.scopedvm.model

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.dhabensky.scopedvm.ScopedViewModelProviders

/**
 * @author dhabensky <dhabensky@yandex.ru>
 */
class FragmentWithViewModel : Fragment() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		println(" - FragmentWithViewModel created")
		ScopedViewModelProviders.forScope(this, null)
				.of(this)
				.get(NestedFragmentViewModel::class.java)
		ScopedViewModelProviders.forScope(this, null)
				.of(activity!!)
				.get(NestedActivityViewModel::class.java)
	}

	override fun onDestroy() {
		println(" - FragmentWithViewModel destroyed")
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
