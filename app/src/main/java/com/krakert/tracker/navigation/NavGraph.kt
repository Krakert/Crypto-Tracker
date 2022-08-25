package com.krakert.tracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.krakert.tracker.ui.ListAddCoin
import com.krakert.tracker.ui.ListOverview
import com.krakert.tracker.ui.ListSettings
import com.krakert.tracker.ui.ShowDetails
import com.krakert.tracker.ui.theme.WearAppTheme

@Composable
fun NavGraph() {
    val navController: NavHostController = rememberSwipeDismissableNavController()
    LocalContext.current
    WearAppTheme {
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = Screen.Overview.route,
        ) {
            composable(Screen.Overview.route) {
                ListOverview(
                    navController = navController,
                    viewModel = hiltViewModel()
                )
            }
            composable(Screen.Add.route) {
                ListAddCoin(
                    viewModel = hiltViewModel()
                )
            }
            composable(Screen.Details.route) {
                val result = navController.previousBackStackEntry?.savedStateHandle?.get<String>("Coin")
                if (result != null) {
                    ShowDetails(
                        coinId = result,
                        viewModel = hiltViewModel(),
                        navController = navController
                    )
                }
            }
            composable(Screen.Settings.route) {
                ListSettings()
            }

        }
    }
}