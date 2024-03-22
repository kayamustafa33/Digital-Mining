package com.kaya.digitalmining.util

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScreen(context : Context,onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    val cardInfoList: List<Triple<String, String, Context>> = listOf(
        Triple("Mining rate 15", "3$", context),
        Triple("Mining rate 20", "5$", context),
        Triple("Mining rate 25", "8$", context),
        Triple("Mining rate 30", "10$", context),
        Triple("Mining rate 35", "13$", context),
        Triple("Mining rate 40", "15$", context)
    )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = Color.White,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(cardInfoList) { item ->
                SubsCardItem(productName = item.first, price = item.second, item.third)
            }
        }
    }
}
