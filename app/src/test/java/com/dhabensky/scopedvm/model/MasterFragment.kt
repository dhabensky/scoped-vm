package com.dhabensky.scopedvm.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

/**
 * @author dhabensky <dhabensky@yandex.ru>
 */
class MasterFragment : Fragment() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		println(" - MasterFragment created")
	}

	override fun onDestroy() {
		println(" - MasterFragment destroyed")
		super.onDestroy()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return FrameLayout(inflater.context).apply {
			layoutParams = ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT
			)
			id = 1
		}
	}

}
