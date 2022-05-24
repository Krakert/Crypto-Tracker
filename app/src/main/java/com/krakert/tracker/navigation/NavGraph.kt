package com.krakert.tracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.krakert.tracker.theme.WearAppTheme
import com.krakert.tracker.theme.initialThemeValues
import com.krakert.tracker.ui.ListAddCoin
import com.krakert.tracker.ui.ListOverview
import com.krakert.tracker.viewmodel.AddCoinViewModel
import com.krakert.tracker.viewmodel.OverviewViewModel

@Composable
fun NavGraph() {
    val navController: NavHostController = rememberSwipeDismissableNavController()
//    val actions = remember(navController) { MainActions(navController) }
    WearAppTheme(colors = initialThemeValues.colors) {
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = Screen.Overview.route,
        ) {
            /**
             * Navigates to [ListOverview].
             */
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

        }
    }
}