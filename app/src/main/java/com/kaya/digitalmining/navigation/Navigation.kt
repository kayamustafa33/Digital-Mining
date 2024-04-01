package com.kaya.digitalmining.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaya.digitalmining.mainView.MiningScreen
import com.kaya.digitalmining.mainView.ProfileScreen
import com.kaya.digitalmining.mainView.news.HomeScreen
import com.kaya.digitalmining.mainView.news.NewsDetail
import com.kaya.digitalmining.mainView.profile.OldMinerScreen
import com.kaya.digitalmining.mainView.profile.RoadmapScreen
import com.kaya.digitalmining.mainView.profile.WalletScreen
import com.kaya.digitalmining.mainView.profile.settings.ResetPasswordScreen
import com.kaya.digitalmining.mainView.profile.settings.SettingsScreen
import com.kaya.digitalmining.model.New
import com.kaya.digitalmining.util.SuccessScreen

@Composable
fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        Modifier.padding(innerPadding)
    ) {
        composable(
            Screen.HomeScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) { HomeScreen(navController = navController) }
        composable(
            Screen.MiningScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) { MiningScreen(context = LocalContext.current) }
        composable(
            Screen.ProfileScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) {
            ProfileScreen(
                context = LocalContext.current,
                navController = navController
            )
        }
        composable(Screen.NewsDetailScreen.route) {
            val new = navController.previousBackStackEntry?.savedStateHandle?.get<New>("new")
            NewsDetail(navController = navController, cryptoNews = new)
        }
        composable(
            "${Screen.ProfileScreen.route}/${Screen.WalletScreen.route}",
            exitTransition = { ExitTransition.None }) { WalletScreen() }
        composable(
            "${Screen.ProfileScreen.route}/${Screen.SettingsScreen.route}",
            exitTransition = { ExitTransition.None }) { SettingsScreen(navController = navController) }
        composable(
            "${Screen.SettingsScreen.route}/${Screen.ResetPasswordScreen.route}",
            exitTransition = { ExitTransition.None }) { ResetPasswordScreen(navController = navController) }
        composable(
            "${Screen.ProfileScreen.route}/${Screen.OldMinerScreen.route}",
            exitTransition = { ExitTransition.None }) { OldMinerScreen() }
        composable(
            "${Screen.ProfileScreen.route}/${Screen.RoadmapScreen.route}",
            exitTransition = { ExitTransition.None }) { RoadmapScreen() }
        composable(
            "${Screen.ResetPasswordScreen.route}/${Screen.SuccessScreen.route}",
            exitTransition = { ExitTransition.None }) { SuccessScreen(navController = navController) }
        composable(
            "${Screen.SuccessScreen.route}/${Screen.ProfileScreen.route}",
            exitTransition = { ExitTransition.None }) {
            ProfileScreen(
                context = LocalContext.current,
                navController = navController
            )
        }
    }
}