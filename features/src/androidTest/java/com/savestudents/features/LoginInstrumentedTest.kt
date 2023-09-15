package com.savestudents.features

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(NavigationActivity::class.java)

    @get:Rule
    val koinTestRule = KoinTestRule()

    @Before
    fun setup() {
        activityRule.scenario.recreate()
    }

    @Test
    fun existEmailField() {
        onView(withId(R.id.email_text_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun existPasswordField() {
        onView(withId(R.id.password_text_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun existRegisterButton() {
        onView(withId(R.id.register_button)).check(matches(isDisplayed()))
    }

    @Test
    fun existSubmitButton() {
        onView(withId(R.id.submit_button)).check(matches(isDisplayed()))
    }

    @Test
    fun showEmptyEmailError() {
        onView(withId(R.id.submit_button)).perform(click())
        onView(withText(R.string.account_validation_empty_email)).check(matches(isDisplayed()))
    }

    @Test
    fun showEmptyPasswordError() {
        val email = "teste@teste.com"
        onView(withId(R.id.email_text_layout)).perform(typeText(email), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.submit_button)).perform(click())
        onView(withText(R.string.account_validation_empty_password)).check(matches(isDisplayed()))
    }

    @Test
    fun showIncorrectEmailFormatError() {
        val email = "teste"
        val password = "123456"
        onView(withId(R.id.email_text_layout)).perform(typeText(email), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.password_text_layout)).perform(typeText(password), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.submit_button)).perform(click())
        onView(withText(R.string.account_validation_incorrect_email)).check(matches(isDisplayed()))
    }
}