package com.krakert.tracker.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.krakert.tracker.viewmodel.DetailsViewModel

@Composable
fun ShowDetails(coin: String?, viewModel: DetailsViewModel, navController: NavHostController){
    println(coin)
}