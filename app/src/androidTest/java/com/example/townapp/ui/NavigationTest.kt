package com.example.townapp.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.townapp.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testBottomNavigation() {
        composeTestRule.onNodeWithText("小镇").assertIsDisplayed()
        composeTestRule.onNodeWithText("居民").assertIsDisplayed()
        composeTestRule.onNodeWithText("物品库").assertIsDisplayed()
        composeTestRule.onNodeWithText("数据板").assertIsDisplayed()
        composeTestRule.onNodeWithText("认知觉醒").assertIsDisplayed()
        composeTestRule.onNodeWithText("设置").assertIsDisplayed()
    }

    @Test
    fun testNavigationToSettings() {
        composeTestRule.onNodeWithText("设置").performClick()
        composeTestRule.onNodeWithText("设置").assertIsDisplayed()
    }

    @Test
    fun testModeToggleButton() {
        composeTestRule.onNodeWithContentDescription("当前为童年模式，点击切换到成人模式").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("当前为童年模式，点击切换到成人模式").performClick()
        composeTestRule.onNodeWithContentDescription("当前为成人模式，点击切换到童年模式").assertIsDisplayed()
    }
}