package com.kaya.digitalmining.mainView.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kaya.digitalmining.R
import com.kaya.digitalmining.model.New

@Composable
fun NewsDetail(navController: NavController, cryptoNews: New?) {
    Column(modifier = Modifier.fillMaxSize()) {
        NewsImage(imageURL = cryptoNews?.image.toString())
        NewsContent(title = cryptoNews?.title.toString(), content = cryptoNews?.content.toString())
    }
}


@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun NewsImage(imageURL: String) {
    GlideImage(
        model = imageURL,
        contentDescription = "crypto-image",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentScale = ContentScale.Crop
    ) { request ->
        request.error(R.drawable.ic_launcher_background)
            .placeholder(R.drawable.progress_animation)
    }
}

@Composable
fun NewsContent(title: String, content: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.Black
    )
    Text(
        text = content,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        fontSize = 18.sp
    )
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewNewsDetail() {
    val navController = NavController(LocalContext.current)
    NewsDetail(navController = navController, New("News Content","", "", "News Title"))
}