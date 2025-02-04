package com.nakyung.assignment_nakyung.ui.navigation

sealed class ScreenDestinations(
    open val route: String,
) {
    data object Search : ScreenDestinations(route = "search")

    data object Detail : ScreenDestinations(route = "detail")
}
