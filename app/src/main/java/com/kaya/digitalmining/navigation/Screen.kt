package com.kaya.digitalmining.navigation

sealed class Screen(val route: String) {

    data object LoginScreen : Screen("loginScreen")
    data object SignUpScreen : Screen("signUpScreen")
    data object HomeScreen : Screen("homeScreen")
    data object MiningScreen : Screen("miningScreen")
    data object ProfileScreen : Screen("profileScreen")
    data object NewsDetailScreen : Screen("newsDetailScreen")
    data object WalletScreen : Screen("walletScreen")
    data object SettingsScreen : Screen("settingsScreen")
    data object ResetPasswordScreen : Screen("resetPasswordScreen")
    data object OldMinerScreen : Screen("oldMinerScreen")
    data object RoadmapScreen : Screen("roadmapScreen")
    data object SuccessScreen : Screen("successScreen")

}