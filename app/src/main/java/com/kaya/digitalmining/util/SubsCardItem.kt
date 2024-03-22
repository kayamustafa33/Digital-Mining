package com.kaya.digitalmining.util

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaya.digitalmining.paymentService.serviceData.IapConnector
import com.kaya.digitalmining.service.GooglePaymentService


@Composable
fun SubsCardItem(productName: String, price : String, context: Context) {

    val packageList = listOf("3$","5$","8$","10$","13$","15$")
    val productIDList = mutableListOf("premium1","premium2","premium3","premium4","premium5","premium6")
    var selectedProductID : String?
    val googlePaymentService : GooglePaymentService = viewModel()
    val iapConnector : IapConnector = googlePaymentService.connectPayment(productIDList,context)
    val productPriceMap = packageList.zip(productIDList).toMap()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(150.dp)
            .fillMaxWidth(),
        colors = CardColors(Color(0xFF283747),Color(0xFF283747),Color(0xFF283747),Color(0xFF283747)),
        onClick = {
            selectedProductID = productPriceMap[price]
            iapConnector.purchase(context as Activity,selectedProductID!!)
            Toast.makeText(context,selectedProductID,Toast.LENGTH_SHORT).show()
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally        ){
            Text(
                text = productName,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = price,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Preview
@Composable
fun ShowCard(){
    SubsCardItem(productName = "Mining rate => 15", price = "3$" ,LocalContext.current)
}