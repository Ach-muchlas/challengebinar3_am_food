package com.am.amfood.utils

import android.app.Activity
import android.view.View
import android.widget.ProgressBar
import androidx.navigation.NavController
import com.am.amfood.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


class UtilsTest {

    /*This is a function for fragment movement*/
    @Test
    fun navigate() {
        val navController = mock<NavController>()
        val destination = "homeToProfile"

        Utils.navigateToDestination(destination, navController)
        verify(navController).navigate(R.id.action_navigation_home_to_navigation_profile)
    }

    /*This function aims to test the manual formatting
    of retrieving email names without @*/
    @Test
    fun formatEmail() {
        assertEquals("achmadmuchlasin", Utils.formatNameFromEmail("achmadmuchlasin@gmail.com"))
    }

    /*This function aims to test the visibility of progress bar*/
    @Test
    fun testSetUpVisibilityProgressBar() {
        val progressBar = mock<ProgressBar>()
        Utils.setUpVisibilityProgressbar(progressBar, true)
        assertEquals(View.VISIBLE, progressBar.visibility)
    }

    /*This function aims to test the gone of progress bar*/
    @Test
    fun testSetUpGoneProgressBar() {
        val progressBar = mock<ProgressBar>()
        Utils.setUpVisibilityProgressbar(progressBar, false)
        verify(progressBar).visibility = View.GONE
    }

    /*This function aims to test the visibility of Bottom navigation*/
    @Test
    fun setUpVisibilityBottomNavigation() {
        val activity = mock<Activity>()
        val bottomNavigationView = mock<BottomNavigationView>()
        val bottomNavId = R.id.bottomNavigation
        `when`(activity.findViewById<BottomNavigationView>(bottomNavId)).thenReturn(
            bottomNavigationView
        )

        Utils.setUpBottomNavigation(activity, false)
        verify(bottomNavigationView).visibility = View.VISIBLE
    }

}