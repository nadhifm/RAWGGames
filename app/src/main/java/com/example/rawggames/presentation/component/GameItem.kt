package com.example.rawggames.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.rawggames.domain.model.Game
import com.example.rawggames.ui.theme.interFamily
import com.example.rawggames.utils.formatDateString

@Composable
fun GameItem(
    onClick: (Long) -> Unit,
    game: Game
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
            )
            .clickable {
                onClick(game.id)
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column {
            SubcomposeAsyncImage(
                model = game.backgroundImage,
                contentDescription = game.name,
                modifier = Modifier.height(180.dp),
                contentScale = ContentScale.Crop
            ) {
                val painterState = painter.state
                if (painterState is AsyncImagePainter.State.Loading || painterState is AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Gray)
                    )
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
            Text(
                text = game.name,
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Realese Date: " + formatDateString(game.released),
                fontFamily = interFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Genres: " + game.genres.joinToString(separator = ", ") { genre -> genre.name },
                fontFamily = interFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}