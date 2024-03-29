package com.qubacy.utility.androidprojecttemplate.ui.application.activity._common.screen._common.fragment._common

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.qubacy.utility.androidprojecttemplate.R
import com.qubacy.utility.androidprojecttemplate._common._test.util.launcher.launchFragmentInHiltContainer
import com.qubacy.utility.androidprojecttemplate.ui.application.activity._common.HiltTestActivity
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.reflect.Field

abstract class BaseFragmentTest<FragmentType : BaseFragment> {
    companion object {
        const val TEST_MESSAGE = "test message"
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    protected lateinit var mActivityScenario: ActivityScenario<HiltTestActivity>
    protected lateinit var mFragment: FragmentType
    protected lateinit var mNavController: TestNavHostController

    @Before
    open fun setup() {
        init()
    }

    abstract fun getFragmentClass(): Class<FragmentType>
    @IdRes
    abstract fun getCurrentDestination(): Int

    protected fun init() {
        mNavController = TestNavHostController(ApplicationProvider.getApplicationContext())

        initFragment()
    }

    private fun retrieveModelFieldReflection(): Field {
        return getFragmentClass()
            .getDeclaredField("mModel\$delegate")
            .apply { isAccessible = true }
    }

    protected open fun getFragmentArgs(): Bundle? {
        return null
    }

    private fun initFragment() {
        mActivityScenario = launchFragmentInHiltContainer(
            fragmentArgs = getFragmentArgs(),
            fragmentClass = getFragmentClass(),
            navHostController = mNavController,
            navHostControllerInitAction = {
                initNavController(this)
            }) {
                initFragmentOnActivity(this)
            }
    }

    private fun initNavController(navController: TestNavHostController) {
        navController.apply {
            setGraph(R.navigation.nav_graph)
            initNavControllerDestination(navController)
        }
    }

    protected open fun initNavControllerDestination(navController: TestNavHostController) {
        navController.setCurrentDestination(getCurrentDestination())
    }

    protected open fun initFragmentOnActivity(fragment: Fragment) {
        mFragment = fragment as FragmentType
    }

    protected fun getCurrentDestinationNavArgs(): Bundle? {
        return mNavController.backStack.last().arguments
    }

    @Test
    open fun showMessageTest() = runTest {
        val onPopupMessageOccurredMethodReflection = BaseFragment::class.java
            .getDeclaredMethod(
                "onPopupMessageOccurred", String::class.java, Int::class.java)
            .apply { isAccessible = true }

        mActivityScenario.onActivity {
            onPopupMessageOccurredMethodReflection.invoke(
                mFragment, TEST_MESSAGE, Toast.LENGTH_SHORT)
        }

        Espresso.onView(withText(TEST_MESSAGE))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}