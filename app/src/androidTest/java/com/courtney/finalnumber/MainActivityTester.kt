package com.courtney.finalnumber

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTester {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun guessWrong() {

        val resources = activityTestRule.activity.resources
        val secret = activityTestRule.activity.secretNumber.secret
        var min = 1
        var max = 100
        var message: String

        for (n in 1..100) {

            if (n != secret) {
                onView(withId(R.id.edt_secret)).perform(clearText())
                onView(withId(R.id.edt_secret)).perform(typeText(n.toString()))
                onView(withId(R.id.btn_guess)).perform(click())
                when {
                    n < secret -> {
                        message =
                            resources.getString(R.string.bigger) + n + "\t" + resources.getString(R.string.to) + "\t" + max
                        min = n
                    }
                    else -> {
                        message =
                            resources.getString(R.string.smaller) + min + "\t" + resources.getString(R.string.to) + "\t" + n
                        max = n
                    }
                }
                onView(withText(message)).check(matches(isDisplayed()))
                onView(withText(resources.getString(R.string.ok))).perform(click())
            }
        }
    }
}

