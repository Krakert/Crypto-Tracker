package com.krakert.tracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.krakert.tracker.ui.tile.theme.WearAppTheme
import com.krakert.tracker.ui.ListAddCoin
import com.krakert.tracker.ui.ListOverview
import com.krakert.tracker.ui.ListSettings
import com.krakert.tracker.ui.ShowDetails
import com.krakert.tracker.ui.viewmodel.DetailsViewModel
import com.krakert.tracker.ui.viewmodel.OverviewViewModel

@Composable
fun NavGraph() {
    val navController: NavHostController = rememberSwipeDismissableNavController()
    val context = LocalContext.current
    WearAppTheme {
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = Screen.Overview.route,
        ) {
            composable(Screen.Overview.route) {
                ListOverview(
                    navController = navController,
                    viewModel = OverviewViewModel(context)
                )
            }
            composable(Screen.Add.route) {
                ListAddCoin()
            }
            composable(Screen.Details.route) {
                val result = navController.previousBackStackEntry?.savedStateHandle?.get<String>("Coin")
                if (result != null) {
                    ShowDetails(
                        coinId = result,
                        viewModel = DetailsViewModel(
                            context = context,
                            coin = result
                        ),
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