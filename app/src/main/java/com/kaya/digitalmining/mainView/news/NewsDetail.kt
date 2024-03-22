package com.kaya.digitalmining.mainView.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun NewsDetail(navController: NavController) {

}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewNewsDetail() {
    val navController = NavController(LocalContext.current)
    NewsDetail(navController = navController)
}