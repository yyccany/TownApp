package com.example.townapp.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.townapp.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testSettingsScreenElements() {
        composeTestRule.onNodeWithText("设置").performClick()

        composeTestRule.onNodeWithText("显示设置").assertIsDisplayed()
        composeTestRule.onNodeWithText("觉醒模式").assertIsDisplayed()
        composeTestRule.onNodeWithText("童年模式").assertIsDisplayed()
        composeTestRule.onNodeWithText("关于").assertIsDisplayed()
    }

    @Test
    fun testAwakeningModeToggle() {
        composeTestRule.onNodeWithText("设置").performClick()
        composeTestRule.onNodeWithText("觉醒模式").performClick()
        composeTestRule.onNodeWithText("觉醒模式").assertIsOn()
    }

    @Test
    fun testChildhoodModeToggle() {
        composeTestRule.onNodeWithText("设置").performClick()
        composeTestRule.onNodeWithText("童年模式").performClick()
        composeTestRule.onNodeWithText("童年模式").assertIsOff()
    }
}