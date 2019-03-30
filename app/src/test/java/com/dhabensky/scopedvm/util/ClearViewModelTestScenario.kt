package com.dhabensky.scopedvm.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.test.core.app.ActivityScenario
import com.dhabensky.scopedvm.ScopedViewModelProviders
import com.dhabensky.scopedvm.model.Holder
import com.dhabensky.scopedvm.model.SpyableViewModel
import com.dhabensky.scopedvm.test.R
import org.mockito.Mockito

/**
 * @author dhabensky <dhabensky@yandex.ru>
 */
class ClearViewModelTestScenario(
    val activityScenario: ActivityScenario<out FragmentActivity>
) {

    fun addFragment(fragment: Fragment): ClearViewModelTestScenario {
        fragments { it.add(fragment) }
        return this
    }

    fun removeFragment(fragment: Fragment): ClearViewModelTestScenario {
        fragments { it.remove(fragment) }
        return this
    }

    fun replaceFragment(fragment: Fragment): ClearViewModelTestScenario {
        fragments { it.replace(fragment) }
        return this
    }

    fun getVanillaViewModelOfFragment(fragment: Fragment, holder: Holder<SpyableViewModel>): ClearViewModelTestScenario {
        activityScenario.onActivity {
            holder.value = ViewModelProviders.of(fragment)
                .get(SpyableViewModel::class.java)
        }
        return this
    }

    fun getVanillaViewModelOfActivity(holder: Holder<SpyableViewModel>): ClearViewModelTestScenario {
        activityScenario.onActivity {
            holder.value = ViewModelProviders.of(it)
                .get(SpyableViewModel::class.java)
        }
        return this
    }

    fun getScopedViewModelOfFragment(fragment: Fragment, holder: Holder<SpyableViewModel>, scope: String?): ClearViewModelTestScenario {
        activityScenario.onActivity {
            holder.value = ScopedViewModelProviders.forScope(fragment, scope)
                .of(fragment)
                .get(SpyableViewModel::class.java)
        }
        return this
    }

    fun getScopedViewModelOfActivity(fragment: Fragment, holder: Holder<SpyableViewModel>, scope: String?): ClearViewModelTestScenario {
        activityScenario.onActivity {
            holder.value = ScopedViewModelProviders.forScope(fragment, scope)
                .of(it)
                .get(SpyableViewModel::class.java)
        }
        return this
    }

    fun moveToState(state: Lifecycle.State): ClearViewModelTestScenario {
        activityScenario.moveToState(state)
        return this
    }

    fun verifyClearedTimes(holder: Holder<SpyableViewModel>, times: Int): ClearViewModelTestScenario {
        Mockito.verify(holder.value!!.spy, Mockito.times(times)).onCleared()
        return this
    }

    fun verifyClearedOnce(holder: Holder<SpyableViewModel>): ClearViewModelTestScenario {
        return verifyClearedTimes(holder, 1)
    }

    fun verifyClearedNever(holder: Holder<SpyableViewModel>): ClearViewModelTestScenario {
        return verifyClearedTimes(holder, 0)
    }

    fun fragments(action: (FragmentHelper) -> Unit): ClearViewModelTestScenario {
        activityScenario.onActivity {
            val helper = FragmentHelper(it, R.id.container)
            action.invoke(helper)
            helper.transaction.commit()
        }
        return this
    }

}
