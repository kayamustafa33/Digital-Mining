package com.kaya.digitalmining.mainView

import androidx.appcompat.widget.SearchView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kaya.digitalmining.R

@Composable
fun HomeScreen(navController: NavController) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Current Date",
            color = Color.DarkGray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 25.dp, top = 15.dp)
        )

        Text(
            text = "Welcome User!",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 25.dp, top = 5.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Test",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(25.dp, 15.dp),
            contentScale = ContentScale.FillWidth
        )

        SearchView()

        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 5.dp)) {
            items(10) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Test",
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp)
                            .padding(top = 10.dp),
                        contentScale = ContentScale.FillWidth
                    )

                    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 10.dp)) {
                        Text(
                            text = "Title",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

    }


}

@Composable
fun SearchView() {
    var searchText by remember { mutableStateOf("") }
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp)
            .border(border = BorderStroke(1.dp, Color.Black), shape = MaterialTheme.shapes.medium),
        color = MaterialTheme.colors.surface
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            keyboardActions = KeyboardActions(
                onSearch = {

                }
            )
        )
    }
}


@Preview
@Composable
fun PreviewHomeScreen() {
    val navController = NavController(LocalContext.current)
    HomeScreen(navController = navController)
}