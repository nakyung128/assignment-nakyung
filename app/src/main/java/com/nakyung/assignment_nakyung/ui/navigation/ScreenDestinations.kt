package com.nakyung.assignment_nakyung.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class ScreenDestinations(
    open val route: String,
) {
    data object Search : ScreenDestinations(route = "search")

    data object Detail : ScreenDestinations(route = "detail") {
        override val route: String
            get() = "detail/{owner}/{repo}"
        val arguments =
            listOf(
                navArgument("owner") { type = NavType.StringType },
                navArgument("repo") { type = NavType.StringType },
            )

        fun createRoute(
            owner: String,
            repo: String,
        ) = "detail/$owner/$repo"
    }
}
