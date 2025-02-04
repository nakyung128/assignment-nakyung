package com.nakyung.assignment_nakyung.ui.main

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nakyung.assignment_nakyung.ui.navigation.AppNavHost
import com.nakyung.assignment_nakyung.ui.navigation.ScreenDestinations

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
    ) { _ ->
        Surface(
            modifier =
                modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
        ) {
            AppNavHost(
                modifier = modifier,
                navController = navController,
                startDestination = ScreenDestinations.Search.route,
            )
        }
    }
}
