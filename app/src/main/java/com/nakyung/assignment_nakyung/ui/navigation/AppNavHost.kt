package com.nakyung.assignment_nakyung.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nakyung.assignment_nakyung.ui.search.SearchRoute

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            route = ScreenDestinations.Search.route,
        ) {
            SearchRoute(
                modifier = modifier,
                navigateToDetail = {
                    navController.navigate(ScreenDestinations.Detail.route)
                },
            )
        }
    }
}
