package com.kaya.digitalmining.mainView.news

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.kaya.digitalmining.R
import com.kaya.digitalmining.model.New
import com.kaya.digitalmining.util.CustomProgressDialog
import com.kaya.digitalmining.viewModel.NewsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeScreen(navController: NavController) {

    val newsViewModel by lazy { NewsViewModel() }
    val progressState = remember { mutableStateOf(true) }
    val cryptoNewsList = remember { mutableStateOf(emptyList<New>()) }
    val filteredList = remember { mutableStateOf(emptyList<New>()) }
    val localLifecycleOwner = LocalLifecycleOwner.current

    newsViewModel.getCryptoNews()

    LaunchedEffect(Unit) {
        newsViewModel.newsData.observe(localLifecycleOwner) { news ->
            if (news != null) {
                cryptoNewsList.value = news.news
                progressState.value = false
                filteredList.value = cryptoNewsList.value
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Current Date",
            color = Color.DarkGray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 25.dp, top = 15.dp)
        )

        Text(
            text = "Welcome, User \uD83D\uDC4B",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 25.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))
        ImageSlider(cryptoNewsList.value)

        SearchView() { searchText ->
            filteredList.value = performSearch(searchText, cryptoNewsList.value)
        }

        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
            CustomProgressDialog(isVisible = progressState.value)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 5.dp)
        ) {
            items(filteredList.value.size) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "new",
                                value = filteredList.value[it]
                            )
                            navController.navigate(route = "newsDetailScreen")
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp)
                            .padding(top = 10.dp)
                    ) {
                        GlideImage(
                            model = filteredList.value[it].image,
                            contentDescription = "crypto-image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(shape = RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop,
                        ) { request ->
                            request.placeholder(R.drawable.progress_animation)
                                .error(R.drawable.ic_launcher_background)
                        }
                    }


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {

                        Text(
                            text = filteredList.value[it].title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = filteredList.value[it].content,
                            maxLines = 2,
                            fontSize = 14.sp,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                }
            }
        }
    }


}

@Composable
fun SearchView(onSearch: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        value = searchText,
        onValueChange = {
            searchText = it
            onSearch(searchText)
        },
        label = { Text("Search") },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            disabledBorderColor = Color.Black,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
            }
        )
    )
}

private fun performSearch(searchText: String, cryptoNewsList: List<New>): List<New> {
    return cryptoNewsList.filter {
        it.title.contains(searchText, ignoreCase = true)
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)
fun ImageSlider(cryptoNewsList: List<New>) {
    val pagerState = rememberPagerState(initialPage = 0)

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(3000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Column {
        HorizontalPager(
            count = cryptoNewsList.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) { page ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                GlideImage(
                    model = cryptoNewsList[page].image,
                    contentDescription = "crypto-image-slider",
                    modifier = Modifier
                        .fillMaxSize()
                        .height(100.dp),
                    contentScale = ContentScale.Crop
                ) { request ->
                    request.error(R.drawable.ic_launcher_background)
                        .placeholder(R.drawable.progress_animation)
                }
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = NavController(LocalContext.current)
    HomeScreen(navController = navController)
}