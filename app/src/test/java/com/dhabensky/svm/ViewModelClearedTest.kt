package com.dhabensky.svm

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import com.dhabensky.svm.test.EmptyActivity
import com.dhabensky.svm.util.ClearViewModelTestScenario
import com.dhabensky.svm.util.ClearViewModelTestScenarioSingleFragment
import com.dhabensky.svm.model.Holder
import com.dhabensky.svm.model.SpyableViewModel
import com.dhabensky.svm.model.TestFragment
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * @author dhabensky <dhabensky@yandex.ru>
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class ViewModelClearedTest {

    @Test
    fun `viewModel of activity cleared when activity destroyed`() {
        val scenario = ClearViewModelTestScenarioSingleFragment(
            ActivityScenario.launch(EmptyActivity::class.java))

        scenario
            .addTestFragment()
            .getViewModelOfActivity()
            .moveToState(Lifecycle.State.RESUMED)

            .removeTestFragment()
            .verifyClearedTimes(0)

            .moveToState(Lifecycle.State.DESTROYED)
            .verifyClearedTimes(1)
    }

    @Test
    fun `viewModel of fragment cleared when fragment destroyed`() {
        val scenario = ClearViewModelTestScenarioSingleFragment(
            ActivityScenario.launch(EmptyActivity::class.java))

        scenario
            .addTestFragment()
            .getViewModelOfFragment()
            .moveToState(Lifecycle.State.RESUMED)

            .removeTestFragment()
            .verifyClearedTimes(1)

            .moveToState(Lifecycle.State.DESTROYED)
            .verifyClearedTimes(1)
    }

    @Test
    fun `activity returns existing viewModel for same scope`() {
        val fragment1 = Fragment()
        val fragment2 = Fragment()
        val holder1 = Holder<SpyableViewModel>()
        val holder2 = Holder<SpyableViewModel>()
        val scope = "scope"
        val scenario = ClearViewModelTestScenario(
            ActivityScenario.launch(EmptyActivity::class.java))

        scenario
            .addFragment(fragment1)
            .getScopedViewModelOfActivity(fragment1, holder1, scope)
            .addFragment(fragment2)
            .getScopedViewModelOfActivity(fragment2, holder2, scope)

        assertNotNull(holder1.value)
        assertTrue(holder1.value === holder2.value)
    }

    @Test
    fun `for activity null-scope provider returns same instance as vanilla provider`() {
        val fragment = Fragment()
        val holder1 = Holder<SpyableViewModel>()
        val holder2 = Holder<SpyableViewModel>()
        val scenario = ClearViewModelTestScenario(
            ActivityScenario.launch(EmptyActivity::class.java))

        scenario
            .addFragment(fragment)
            .getVanillaViewModelOfActivity(holder2)
            .getScopedViewModelOfActivity(fragment, holder1, null)

        assertNotNull(holder1.value)
        assertTrue(holder1.value === holder2.value)
    }

    @Test
    fun `for activity vanilla provider returns same instance as null-scope provider`() {
        val fragment = Fragment()
        val holder1 = Holder<SpyableViewModel>()
        val holder2 = Holder<SpyableViewModel>()
        val scenario = ClearViewModelTestScenario(
            ActivityScenario.launch(EmptyActivity::class.java))

        scenario
            .addFragment(fragment)
            .getScopedViewModelOfActivity(fragment, holder1, null)
            .getVanillaViewModelOfActivity(holder2)

        assertNotNull(holder1.value)
        assertTrue(holder1.value === holder2.value)
    }

    @Test
    fun `for fragment null-scope provider returns same instance as vanilla provider`() {
        val fragment = Fragment()
        val holder1 = Holder<SpyableViewModel>()
        val holder2 = Holder<SpyableViewModel>()
        val scenario = ClearViewModelTestScenario(
            ActivityScenario.launch(EmptyActivity::class.java))

        scenario
            .addFragment(fragment)
            .getVanillaViewModelOfFragment(fragment, holder2)
            .getScopedViewModelOfFragment(fragment, holder1, null)

        assertNotNull(holder1.value)
        assertTrue(holder1.value === holder2.value)
    }

    @Test
    fun `for fragment vanilla provider returns same instance as null-scope provider`() {
        val fragment = Fragment()
        val holder1 = Holder<SpyableViewModel>()
        val holder2 = Holder<SpyableViewModel>()
        val scenario = ClearViewModelTestScenario(
            ActivityScenario.launch(EmptyActivity::class.java))

        scenario
            .addFragment(fragment)
            .getScopedViewModelOfFragment(fragment, holder1, null)
            .getVanillaViewModelOfFragment(fragment, holder2)

        assertNotNull(holder1.value)
        assertTrue(holder1.value === holder2.value)
    }

    @Test
    fun `scope cleared when its only fragment destroyed`() {
        val fragment1 = Fragment()
        val holder = Holder<SpyableViewModel>()
        val scope = "scope"
        val scenario = ClearViewModelTestScenario(
            ActivityScenario.launch(EmptyActivity::class.java))

        scenario
            .addFragment(fragment1)
            .getScopedViewModelOfActivity(fragment1, holder, scope)

            .moveToState(Lifecycle.State.RESUMED)
            .verifyClearedNever(holder)

            .removeFragment(fragment1)
            .verifyClearedOnce(holder)

            .moveToState(Lifecycle.State.DESTROYED)
            .verifyClearedOnce(holder)
    }

    @Test
    fun `scope cleared when all its fragments destroyed`() {
        val fragment1 = Fragment()
        val fragment2 = Fragment()
        val holder = Holder<SpyableViewModel>()
        val scope = "scope"
        val scenario = ClearViewModelTestScenario(
            ActivityScenario.launch(EmptyActivity::class.java))

        scenario
            .addFragment(fragment1)
            .getScopedViewModelOfActivity(fragment1, holder, scope)
            .addFragment(fragment2)
            .getScopedViewModelOfActivity(fragment2, holder, scope)

            .moveToState(Lifecycle.State.RESUMED)
            .verifyClearedNever(holder)

            .removeFragment(fragment2)
            .verifyClearedNever(holder)

            .removeFragment(fragment1)
            .verifyClearedOnce(holder)

            .moveToState(Lifecycle.State.DESTROYED)
            .verifyClearedOnce(holder)
    }

    @Test
    fun `scope cleared when its activity destroyed`() {
        val fragment1 = Fragment()
        val fragment2 = Fragment()
        val holder = Holder<SpyableViewModel>()
        val scope = "scope"
        val scenario = ClearViewModelTestScenario(
            ActivityScenario.launch(EmptyActivity::class.java))

        scenario
            .addFragment(fragment1)
            .getScopedViewModelOfActivity(fragment1, holder, scope)
            .addFragment(fragment2)
            .getScopedViewModelOfActivity(fragment2, holder, scope)

            .moveToState(Lifecycle.State.RESUMED)
            .verifyClearedNever(holder)

            .moveToState(Lifecycle.State.DESTROYED)
            .verifyClearedOnce(holder)
    }

    @Test
    fun `scope not cleared when the only scope fragment replaced with another one`() {
        val scope = "scope"
        val fragment1 = TestFragment(scope)
        val fragment2 = TestFragment(scope)
        val holder = Holder<SpyableViewModel>()
        val scenario = ClearViewModelTestScenario(
            ActivityScenario.launch(EmptyActivity::class.java))

        scenario
            .addFragment(fragment1)
            .getScopedViewModelOfActivity(fragment1, holder, scope)

            .moveToState(Lifecycle.State.RESUMED)
            .verifyClearedNever(holder)

            .fragments { it.replace(fragment2) }
            .verifyClearedNever(holder)

            .removeFragment(fragment2)
            .verifyClearedOnce(holder)

            .moveToState(Lifecycle.State.DESTROYED)
            .verifyClearedOnce(holder)
    }

}
