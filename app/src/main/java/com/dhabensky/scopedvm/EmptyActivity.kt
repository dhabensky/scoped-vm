package com.dhabensky.scopedvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dhabensky.scopedvm.test.R

class EmptyActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.empty_activity)
	}

}
