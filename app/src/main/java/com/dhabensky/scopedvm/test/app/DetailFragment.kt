package com.dhabensky.scopedvm.test.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.dhabensky.scopedvm.test.ScopedFragment

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
class DetailFragment() : ScopedFragment() {

	private var tempData: String? = null

	@SuppressLint("ValidFragment")
	constructor(tempData: String?) : this() {
		this.tempData = tempData
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.data = tempData
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		view.setOnClickListener {
//			replaceFragment(SearchFragment())
		}
	}

}
