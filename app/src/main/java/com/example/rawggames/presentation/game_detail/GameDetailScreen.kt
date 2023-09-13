package com.example.rawggames.presentation.game_detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.rawggames.presentation.component.PaggingError
import com.example.rawggames.ui.theme.interFamily
import com.example.rawggames.utils.formatDateString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(
    navigateBack: () -> Unit,
    viewModel: GameDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Scaffold {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
          item {
              if (state.isLoading) {
                  Box(
                      contentAlignment = Alignment.Center,
                      modifier = Modifier.fillMaxSize().padding(32.dp)
                  ) {
                      CircularProgressIndicator()
                  }
              } else if (state.isError) {
                  PaggingError(
                      message = state.message,
                      onRetry = {
                          viewModel.onEvent(GameDetailEvent.RefreshGameDetail)
                      },
                  )
              } else {
                  Box(contentAlignment = Alignment.TopCenter) {
                      SubcomposeAsyncImage(
                          model = state.gameDetail.backgroundImage,
                          contentDescription = state.gameDetail.name,
                          modifier = Modifier.height(250.dp),
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
                      Box(modifier = Modifier.matchParentSize().background(
                          Brush.verticalGradient(
                          colors = listOf(Color.Black.copy(alpha = 0.5f), Color.Transparent,),
                          startY = 250.toFloat()/3,  // 1/3
                          endY = 250.toFloat()
                      )))
                      Row(
                          horizontalArrangement = Arrangement.SpaceBetween,
                          verticalAlignment = Alignment.CenterVertically,
                          modifier = Modifier
                              .fillMaxWidth()
                              .padding(16.dp)
                      ) {
                          IconButton(onClick = navigateBack) {
                              Icon(
                                  imageVector = Icons.Filled.ArrowBack,
                                  contentDescription = "Back",
                                  tint = Color.White,
                              )
                          }
                          IconButton(
                              onClick = {
                                  viewModel.onEvent(GameDetailEvent.OnFavoriteChange)
                              }
                          ) {
                              if (state.isFavorite) {
                                  Icon(
                                      imageVector = Icons.Filled.Favorite,
                                      contentDescription = "Favorite",
                                      tint = Color.White,
                                  )
                              } else {
                                  Icon(
                                      imageVector = Icons.Filled.FavoriteBorder,
                                      contentDescription = "Favorite",
                                      tint = Color.White,
                                  )
                              }
                          }
                      }
                  }
                  Spacer(modifier = Modifier.height(16.dp))
                  Text(
                      text = state.gameDetail.name,
                      fontFamily = interFamily,
                      fontWeight = FontWeight.Bold,
                      fontSize = 24.sp,
                      modifier = Modifier.padding(horizontal = 16.dp)
                  )
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                      text = "Realese Date: " + formatDateString(state.gameDetail.released),
                      fontFamily = interFamily,
                      fontWeight = FontWeight.Normal,
                      fontSize = 16.sp,
                      modifier = Modifier.padding(horizontal = 16.dp)
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                      text = "Genres: " + state.gameDetail.genres.joinToString(separator = ", ") { genre -> genre.name },
                      fontFamily = interFamily,
                      fontWeight = FontWeight.Normal,
                      fontSize = 16.sp,
                      modifier = Modifier.padding(horizontal = 16.dp)
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                      text = "Genres: " + state.gameDetail.developers.joinToString(separator = ", ") { developer -> developer.name },
                      fontFamily = interFamily,
                      fontWeight = FontWeight.Normal,
                      fontSize = 16.sp,
                      modifier = Modifier.padding(horizontal = 16.dp)
                  )
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                      text = "Description",
                      fontFamily = interFamily,
                      fontWeight = FontWeight.SemiBold,
                      fontSize = 18.sp,
                      modifier = Modifier.padding(horizontal = 16.dp)
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                      text = state.gameDetail.description,
                      fontFamily = interFamily,
                      fontWeight = FontWeight.Normal,
                      fontSize = 16.sp,
                      modifier = Modifier.padding(horizontal = 16.dp)
                  )
                  Spacer(modifier = Modifier.height(32.dp))
              }
          }
        }
    }
}