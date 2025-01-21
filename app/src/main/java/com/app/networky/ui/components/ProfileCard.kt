package com.app.networky.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import com.app.networky.ui.theme.ElementBackground
import com.app.networky.ui.theme.BackgroundDark
import com.app.networky.ui.theme.MainText
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    onProfileUpdate: (ProfileData) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var profileData by remember { mutableStateOf<ProfileData?>(null) }

    Card(
        modifier = modifier.clickable { showBottomSheet = true },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = ElementBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (profileData == null) {
                // Дефолтное состояние
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
            } else {
                Text(
                    text = "Твоя Карта",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Gray
                )
                // Заполненное состояние
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = profileData?.name ?: "",
                            style = MaterialTheme.typography.titleLarge,
                            color = MainText
                        )
                        Text(
                            text = profileData?.description ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MainText
                        )
                    }

                    Spacer(modifier = Modifier.width(160.dp))

                    // Фото профиля
                    if (profileData?.photoUri != null) {
                        AsyncImage(
                            model = profileData!!.photoUri,
                            contentDescription = "Profile photo",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(ElementBackground),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile photo placeholder",
                                modifier = Modifier.size(30.dp),
                                tint = Color.Gray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Нажмите, чтобы открыть QR код и быстро поделится своим профилем с людьми.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        }
    }

    if (showBottomSheet) {
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState,
            dragHandle = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Close",
                        tint = Color.Gray
                    )
                }
            }
        ) {
            ProfileEditor(
                initialProfile = profileData,
                onSave = { newProfileData ->
                    profileData = newProfileData
                    onProfileUpdate(newProfileData)
                    showBottomSheet = false
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
