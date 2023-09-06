package com.krakert.tracker.ui.app

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.krakert.tracker.ui.components.Screen
import com.krakert.tracker.ui.theme.TrackerAppTheme
import com.krakert.tracker.ui.tracker.add.TrackerAddCoinScreen
import com.krakert.tracker.ui.tracker.detail.TrackerDetailScreen
import com.krakert.tracker.ui.tracker.overview.TrackerOverviewScreen
import com.krakert.tracker.ui.tracker.settings.TrackerSettingsScreen

@Composable
fun NavGraph() {
    val navController: NavHostController = rememberSwipeDismissableNavController()

    TrackerAppTheme {
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = Screen.Add.route,
        ) {
            composable(Screen.Overview.route) {
                TrackerOverviewScreen(
                    navController = navController,
                    viewModel = hiltViewModel()
                )
            }
            composable(Screen.Add.route) {
                TrackerAddCoinScreen(
                    viewModel = hiltViewModel()
                )
            }
            composable(Screen.Details.route) {
                val result = navController.previousBackStackEntry?.savedStateHandle?.get<String>("Coin")
                if (result != null) {
                    TrackerDetailScreen(
                        coinId = result,
                        viewModel = hiltViewModel(),
                        navController = navController
                    )
                }
            }
            composable(Screen.Settings.route) {
                TrackerSettingsScreen(
                    viewModel = hiltViewModel()
                )
            }

        }
    }
}