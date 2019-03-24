package com.dhabensky.svm.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.test.core.app.ActivityScenario
import com.dhabensky.svm.ScopedViewModelProviders
import com.dhabensky.svm.test.EmptyActivity
import com.dhabensky.svm.test.R
import org.mockito.Mockito

class ClearViewModelTestScenario {

    val fragment = Fragment()
    var viewModel: SpyableViewModel? = null
    val scenario = ActivityScenario.launch(EmptyActivity::class.java)


    fun addTestFragment(): ClearViewModelTestScenario {
        fragments { it.add(fragment) }
        return this
    }

    fun removeTestFragment(): ClearViewModelTestScenario {
        fragments { it.remove(fragment) }
        return this
    }

    fun getVanillaViewModelForFragment(): ClearViewModelTestScenario {
        scenario.onActivity {
            viewModel = ViewModelProviders.of(fragment)
                .get(SpyableViewModel::class.java)
        }
        return this
    }

    fun getViewModelForFragment(): ClearViewModelTestScenario {
        scenario.onActivity {
            viewModel = ScopedViewModelProviders.forScope(fragment, null)
                .of(fragment)
                .get(SpyableViewModel::class.java)
        }
        return this
    }

    fun getViewModelForActivity(): ClearViewModelTestScenario {
        scenario.onActivity {
            viewModel = ScopedViewModelProviders.forScope(fragment, null)
                .of(it)
                .get(SpyableViewModel::class.java)
        }
        return this
    }

    fun moveToState(state: Lifecycle.State): ClearViewModelTestScenario {
        scenario.moveToState(state)
        return this
    }

    fun verifyOnClearedTimes(times: Int): ClearViewModelTestScenario {
        Mockito.verify(viewModel!!.spy, Mockito.times(times)).onCleared()
        return this
    }

    fun fragments(action: (FragmentHelper) -> Unit): ClearViewModelTestScenario {
        scenario.onActivity {
            val helper = FragmentHelper(it, R.id.container)
            action.invoke(helper)
            helper.transaction.commit()
        }
        return this
    }

}
