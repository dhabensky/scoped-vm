package com.dhabensky.scopedvm.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dhabensky.scopedvm.test.app.MainFragment

class MainActivity : AppCompatActivity() {

	companion object {
		const val TAG = "SAMPLE"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val fm = supportFragmentManager
		if (fm.findFragmentByTag(TAG) == null) {
			fm.beginTransaction()
					.replace(R.id.container, MainFragment(), TAG)
					.commit()
		}
	}

}
