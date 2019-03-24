package com.dhabensky.svm

import androidx.lifecycle.Lifecycle
import com.dhabensky.svm.util.ClearViewModelTestScenario
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class ViewModelClearedTest {

    @Test
    fun `viewmodel for activity cleared when activity destroyed`() {
        val scenario = ClearViewModelTestScenario()
        scenario
            .addTestFragment()
            .getViewModelForActivity()
            .moveToState(Lifecycle.State.RESUMED)
            .removeTestFragment()
            .verifyOnClearedTimes(0)
            .moveToState(Lifecycle.State.DESTROYED)
            .verifyOnClearedTimes(1)
    }

    @Test
    fun `viewmodel for fragment cleared when fragment destroyed`() {
        val scenario = ClearViewModelTestScenario()
        scenario
            .addTestFragment()
            .getViewModelForFragment()
            .moveToState(Lifecycle.State.RESUMED)
            .removeTestFragment()
            .verifyOnClearedTimes(1)
            .moveToState(Lifecycle.State.DESTROYED)
            .verifyOnClearedTimes(1)
    }

}
