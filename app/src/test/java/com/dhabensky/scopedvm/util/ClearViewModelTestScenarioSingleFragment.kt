package com.dhabensky.scopedvm.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import com.dhabensky.scopedvm.model.Holder
import com.dhabensky.scopedvm.model.SpyableViewModel

/**
 * @author dhabensky <dhabensky@yandex.ru>
 */
class ClearViewModelTestScenarioSingleFragment(
    activityScenario: ActivityScenario<out FragmentActivity>
) {

    val fragment = Fragment()
    val holder = Holder<SpyableViewModel>()
    val scenario = ClearViewModelTestScenario(activityScenario)


    fun addTestFragment(): ClearViewModelTestScenarioSingleFragment {
        scenario.addFragment(fragment)
        return this
    }

    fun removeTestFragment(): ClearViewModelTestScenarioSingleFragment {
        scenario.removeFragment(fragment)
        return this
    }

    fun getViewModelOfFragment(): ClearViewModelTestScenarioSingleFragment {
        scenario.getScopedViewModelOfFragment(fragment, holder, null)
        return this
    }

    fun getViewModelOfActivity(): ClearViewModelTestScenarioSingleFragment {
        scenario.getScopedViewModelOfActivity(fragment, holder, null)
        return this
    }

    fun moveToState(state: Lifecycle.State): ClearViewModelTestScenarioSingleFragment {
        scenario.moveToState(state)
        return this
    }

    fun verifyClearedTimes(times: Int): ClearViewModelTestScenarioSingleFragment {
        scenario.verifyClearedTimes(holder, times)
        return this
    }

    fun fragments(action: (FragmentHelper) -> Unit): ClearViewModelTestScenarioSingleFragment {
        scenario.fragments(action)
        return this
    }

}
