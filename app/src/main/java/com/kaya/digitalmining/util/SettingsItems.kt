package com.kaya.digitalmining.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kaya.digitalmining.R
import com.kaya.digitalmining.mainView.profile.settings.ResetPasswordScreen

@Composable
fun SettingsItems(navController: NavController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardColors(
            Color(0xff1E2329),
            Color(0xff1E2329),
            Color(0xff1E2329),
            Color(0xff1E2329)
        )
    ) {
        SettingsText(item = "Notifications", drawableResource = R.drawable.baseline_circle_notifications_24, navController = navController, null)
        Divider()
        SettingsText(item = "Password Change", drawableResource = R.drawable.baseline_lock_reset_24, navController = navController,"resetPasswordScreen")
        Divider()
        SettingsText(item = "Delete Account", drawableResource = R.drawable.baseline_no_accounts_24, navController = navController, null)
        Divider()
        SettingsText(item = "Clear Cache", drawableResource = R.drawable.baseline_cached_24, navController = navController, null)
    }

}

@Composable
fun FeedbackItems(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardColors(
            Color(0xff1E2329),
            Color(0xff1E2329),
            Color(0xff1E2329),
            Color(0xff1E2329)
        )
    ) {
        SettingsText(item = "Report a bug", drawableResource = R.drawable.baseline_bug_report_24, navController = navController, route = null)
        Divider()
        SettingsText(item = "Rate the App", drawableResource = R.drawable.baseline_star_rate_24, navController = navController, route = null)
    }
}

@Composable
fun SettingsText(item: String, drawableResource: Int, navController: NavController, route: String?) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 0.dp)
                .clickable {
                    navController.navigate("settingsScreen/$route")
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {

        Icon(
            painter = painterResource(id = drawableResource),
            contentDescription = null,
            tint = Color.White
        )

        Text(
            text = item,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 15.dp)
                .weight(1f),
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }

}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewSettingsItem() {
    Column {
        SettingsItems(navController = rememberNavController())
        Spacer(modifier = Modifier.padding(15.dp))
        FeedbackItems(navController = rememberNavController())
    }
}