package com.krakert.tracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.krakert.tracker.theme.WearAppTheme
import com.krakert.tracker.ui.ListAddCoin
import com.krakert.tracker.ui.ListOverview
import com.krakert.tracker.ui.ListSettings
import com.krakert.tracker.ui.ShowDetails
import com.krakert.tracker.viewmodel.AddCoinViewModel
import com.krakert.tracker.viewmodel.DetailsViewModel
import com.krakert.tracker.viewmodel.OverviewViewModel
import com.krakert.tracker.viewmodel.SettingsViewModel

@Composable
fun NavGraph() {
    val navController: NavHostController = rememberSwipeDismissableNavController()
    WearAppTheme {
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = Screen.Overview.route,
        ) {
            composable(Screen.Overview.route) {
                ListOverview(
                    navController = navController,
                    viewModel = OverviewViewModel()
                )
            }
            composable(Screen.Add.route) {
                ListAddCoin(
                    navController = navController,
                    viewModel = AddCoinViewModel()
                )
            }
            composable(Screen.Details.route) { backStackEntry ->
                ShowDetails(
                    coin = backStackEntry.arguments?.getString("coin"),
                    viewModel = DetailsViewModel(),
                    navController = navController)
            }
            composable(Screen.Settings.route) {
                ListSettings(
                    viewModel = SettingsViewModel(),
                    navController = navController)
            }

        }
    }
}