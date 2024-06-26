package com.kaya.digitalmining.mainView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.kaya.digitalmining.navigation.Navigation
import com.kaya.digitalmining.util.BottomNavItem

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val selectedItemIndex = remember { mutableIntStateOf(0) }
            val navController = rememberNavController()

            val items = listOf(
                BottomNavItem(label = "Home", selectedIcon = Icons.Filled.Home, unSelectedIcon = Icons.Outlined.Home, route = "homeScreen"),
                BottomNavItem(label = "Mining", selectedIcon = Icons.Filled.AddCircle, unSelectedIcon = Icons.Outlined.AddCircle, route = "miningScreen"),
                BottomNavItem(label = "Profile", selectedIcon = Icons.Filled.Person, unSelectedIcon = Icons.Outlined.Person, route = "profileScreen")
            )

            Scaffold (
                bottomBar = {
                    BottomNavigation (
                        backgroundColor = Color(0xFF283747),
                        elevation = 8.dp,
                    ){
                        items.forEachIndexed { index, bottomNavItem ->
                            BottomNavigationItem(
                                selected = selectedItemIndex.intValue == index,
                                onClick = {
                                    selectedItemIndex.intValue = index
                                    navController.navigate(bottomNavItem.route){
                                        popUpTo(navController.graph.id){
                                            inclusive = true
                                        }
                                    }
                                },
                                label = {
                                    Text(
                                        text = bottomNavItem.label,
                                        color = if (selectedItemIndex.intValue == index) Color.White else Color.Gray
                                    )
                                } ,
                                alwaysShowLabel = false,
                                icon = {
                                    Icon(
                                        imageVector = if (selectedItemIndex.intValue == index) bottomNavItem.selectedIcon else bottomNavItem.unSelectedIcon,
                                        contentDescription = null,
                                        tint = if (selectedItemIndex.intValue == index) Color.White else Color.Gray,
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp)
                                            .size(24.dp)
                                    )
                                })
                        }
                    }

                }) { innerPadding ->
                Navigation(navController = navController, innerPadding = innerPadding)
            }
        }
    }
}
