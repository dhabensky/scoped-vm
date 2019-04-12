package com.dhabensky.scopedvm.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.test.core.app.ActivityScenario
import com.dhabensky.scopedvm.ScopedViewModelProviders
import com.dhabensky.scopedvm.model.Holder
import com.dhabensky.scopedvm.model.TestViewModel
import com.dhabensky.scopedvm.subscription.SubscriptionHost
import com.dhabensky.scopedvm.test.R
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize

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

	fun getVanillaViewModelOfFragment(fragment: Fragment, holder: Holder<TestViewModel>): ClearViewModelTestScenario {
		activityScenario.onActivity {
			holder.value = ViewModelProviders.of(fragment)
					.get(TestViewModel::class.java)
		}
		return this
	}

	fun getVanillaViewModelOfActivity(holder: Holder<TestViewModel>): ClearViewModelTestScenario {
		activityScenario.onActivity {
			holder.value = ViewModelProviders.of(it)
					.get(TestViewModel::class.java)
		}
		return this
	}

	fun getScopedViewModelOfFragment(fragment: Fragment, holder: Holder<TestViewModel>, scope: String?): ClearViewModelTestScenario {
		activityScenario.onActivity {
			holder.value = ScopedViewModelProviders.forScope(fragment, scope)
					.of(fragment)
					.get(TestViewModel::class.java)
		}
		return this
	}

	fun getScopedViewModelOfActivity(fragment: Fragment, holder: Holder<TestViewModel>, scope: String?): ClearViewModelTestScenario {
		activityScenario.onActivity {
			holder.value = ScopedViewModelProviders.forScope(fragment, scope)
					.of(it)
					.get(TestViewModel::class.java)
		}
		return this
	}

	fun moveToState(state: Lifecycle.State): ClearViewModelTestScenario {
		activityScenario.moveToState(state)
		return this
	}

	fun verifyClearedTimes(holder: Holder<TestViewModel>, times: Int): ClearViewModelTestScenario {
		assertThat(holder.value!!.clearedTimes, equalTo(times))
		return this
	}

	fun verifyClearedOnce(holder: Holder<TestViewModel>): ClearViewModelTestScenario {
		return verifyClearedTimes(holder, 1)
	}

	fun verifyClearedNever(holder: Holder<TestViewModel>): ClearViewModelTestScenario {
		return verifyClearedTimes(holder, 0)
	}

	fun verifySubscriptionCount(holder: Holder<SubscriptionHost>, count: Int): ClearViewModelTestScenario {
		assertThat(holder.value!!.subscriptions, hasSize(count))
		return this
	}

	fun onActivity(action: (FragmentActivity) -> Unit): ClearViewModelTestScenario {
		activityScenario.onActivity(action)
		return this
	}

	fun recreate(): ClearViewModelTestScenario {
		activityScenario.recreate()
		return this
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
