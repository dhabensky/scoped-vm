package com.dhabensky.svm

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario.launch
import com.dhabensky.svm.test.EmptyActivity
import com.dhabensky.svm.util.SpyableViewModel
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class ViewModelClearedTest {

    @Test
    fun `viewmodel for activity cleared when activity destroyed`() {
        val scenario = TestCaseScenario()
        scenario
            .addFragment()
            .getViewModelForActivity()
            .moveToState(Lifecycle.State.RESUMED)
            .removeFragment()
            .verifyOnClearedTimes(0)
            .moveToState(Lifecycle.State.DESTROYED)
            .verifyOnClearedTimes(1)
    }

    @Test
    fun `viewmodel for fragment cleared when fragment destroyed`() {
        val scenario = TestCaseScenario()
        scenario
            .addFragment()
            .getViewModelForFragment()
            .moveToState(Lifecycle.State.RESUMED)
            .removeFragment()
            .verifyOnClearedTimes(1)
            .moveToState(Lifecycle.State.DESTROYED)
            .verifyOnClearedTimes(1)
    }


    class TestCaseScenario {

        val fragment = Fragment()
        var vm: SpyableViewModel? = null
        val scenario = launch(EmptyActivity::class.java)


        fun addFragment(): TestCaseScenario {
            scenario
                .onActivity {
                    val fm = it.supportFragmentManager
                    fm.beginTransaction()
                        .add(fragment, null)
                        .commitNow()
                }
            return this
        }

        fun removeFragment(): TestCaseScenario {
            scenario
                .onActivity {
                    val fm = it.supportFragmentManager
                    fm.beginTransaction()
                        .remove(fragment)
                        .commitNow()
                }
            return this
        }

        fun getViewModelForFragment(): TestCaseScenario {
            scenario
                .onActivity {
                    vm = ScopedViewModelProviders.forScope(fragment, null)
                        .of(fragment)
                        .get(SpyableViewModel::class.java)
                }
            return this
        }

        fun getViewModelForActivity(): TestCaseScenario {
            scenario
                .onActivity {
                    vm = ScopedViewModelProviders.forScope(fragment, null)
                        .of(it)
                        .get(SpyableViewModel::class.java)
                }
            return this
        }

        fun moveToState(state: Lifecycle.State): TestCaseScenario {
            scenario.moveToState(state)
            return this
        }

        fun verifyOnClearedTimes(times: Int): TestCaseScenario {
            verify(vm!!.spy, times(times)).onCleared()
            return this
        }

    }

}
