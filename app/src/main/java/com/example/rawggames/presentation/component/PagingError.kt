package com.example.rawggames.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.rawggames.domain.model.Game
import com.example.rawggames.ui.theme.interFamily

@Composable
fun PaggingError(
    message: String,
    onRetry: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = message,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = interFamily,
        )
        Spacer(modifier = Modifier.height(8.dp))
        ElevatedButton(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1B367B),
            ),
        ) {
            Text(
                text = "Retry",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = interFamily,
                color = Color.White,
            )
        }
    }
}