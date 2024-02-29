package com.kaya.digitalmining.mainView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.kaya.digitalmining.util.BottomNavItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var selectedItemIndex by remember { mutableIntStateOf(0) }
            val navController = rememberNavController()

            val items = listOf(
                BottomNavItem(
                    label = "Home",
                    selectedIcon = Icons.Filled.Home,
                    unSelectedIcon = Icons.Outlined.Home,
                    route = "homeScreen"
                ),
                BottomNavItem(
                    label = "Mining",
                    selectedIcon = Icons.Filled.AddCircle,
                    unSelectedIcon = Icons.Outlined.AddCircle,
                    route = "miningScreen"
                ),
                BottomNavItem(
                    label = "Profile",
                    selectedIcon = Icons.Filled.Person,
                    unSelectedIcon = Icons.Outlined.Person,
                    route = "profileScreen"
                )
            )
            
            Scaffold (
                bottomBar = {
                    NavigationBar (
                        containerColor = Color(0xFF283747),
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .height(90.dp)
                    ) {
                        items.forEachIndexed { index, bottomNavItem ->
                            NavigationBarItem(
                                selected = selectedItemIndex == index,
                                onClick = {
                                          selectedItemIndex = index
                                },
                                label = {
                                        Text(
                                            text = bottomNavItem.label,
                                            color = if (selectedItemIndex == index) Color.White else Color.Gray
                                        )
                                },
                                alwaysShowLabel = false,
                                icon = {
                                    Icon(
                                        imageVector = if (selectedItemIndex == index) bottomNavItem.selectedIcon else bottomNavItem.unSelectedIcon,
                                        contentDescription = null,
                                        tint = if (selectedItemIndex == index) Color.White else Color.Gray,
                                        modifier = Modifier
                                    )
                                })
                        }
                    }
                } ){
                    innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    when (selectedItemIndex) {
                        0 -> HomeScreen(navController = navController)
                        1 -> MiningScreen()
                        2 -> ProfileScreen(navController = navController)
                    }
                }
            }
        }
    }
}