package com.app.networky.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import com.app.networky.ui.theme.ElementBackground
import com.app.networky.ui.theme.BackgroundDark
import com.app.networky.ui.theme.MainText

@Composable
fun Home(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {

        SearchSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )


        ProfileCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )


        MeetupsList(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 15.dp)
        )
    }
}

@Composable
private fun SearchSection(
    modifier: Modifier = Modifier
) {
    var isSearchActivated by remember { mutableStateOf(false) }

    val heightAnimation by animateFloatAsState(
        targetValue = if (isSearchActivated) 1f else 0f,
        label = "height"
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    val (_, y) = dragAmount
                    if (y > 0) {
                        isSearchActivated = true
                    }
                    change.consume()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            modifier = Modifier
                .width(140.dp)
                .height(38.dp),
            interactionSource = interactionSource,
            onSearchClick = { isSearchActivated = true }
        )

        SearchHint(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}


@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(35.dp))
            .background(color = ElementBackground)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = onSearchClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            modifier = Modifier.padding(start = 16.dp),
            tint = MainText
        )

        Text(
            text = "Поиск",
            style = MaterialTheme.typography.bodySmall,
            color = MainText,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
        )
    }
}

@Composable
private fun SearchHint(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = "Swipe down",
            modifier = Modifier.padding(end = 8.dp),
            tint = Color.Gray
        )

        Text(
            text = "Нажмите или проведите пальцем вниз, чтобы искать митапы",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Composable
private fun ProfileCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                containerColor = ElementBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Твоя Карта",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Нажми, чтобы создать карточку своего профиля. Расскажи о себе. Не стесняйся :)",
                style = MaterialTheme.typography.bodyLarge,
                color = MainText
            )
        }
    }
}

@Composable
private fun MeetupsList(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(20) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = BackgroundDark)
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Meetup ${index + 1}",
                        color = Color.Gray,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}