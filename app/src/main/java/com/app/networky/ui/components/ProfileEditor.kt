package com.app.networky.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.networky.ui.theme.ElementBackground
import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.app.networky.ui.components.BigButton

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileEditor(
    initialProfile: ProfileData?,
    onSave: (ProfileData) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(initialProfile?.name ?: "") }
    var description by remember { mutableStateOf(initialProfile?.description ?: "") }
    var link by remember { mutableStateOf(initialProfile?.link ?: "") }
    var photoUri by remember { mutableStateOf<Uri?>(initialProfile?.photoUri?.let { Uri.parse(it) }) }

    val context = LocalContext.current

    val permissionState = rememberPermissionState(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    )

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { photoUri = it }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
       //TODO - фиксануть синк выбранной фотки после заполнения карточки
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(ElementBackground)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    if (permissionState.status.isGranted) {
                        photoPickerLauncher.launch("image/*")
                    } else {
                        permissionState.launchPermissionRequest()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (photoUri != null) {
                AsyncImage(
                    model = photoUri,
                    contentDescription = "Profile photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Add photo",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Gray
                )
            }
        }
//TODO - сделать поля кастомного стиля, крч избавиться от Material UI
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Как Вас зовут?") },
            modifier = Modifier
                .fillMaxWidth()


        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Напиши пару слов о себе") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        OutlinedTextField(
            value = link,
            onValueChange = { link = it },
            label = { Text("Вставь сюда ссылку на свой сайт или CV") },
            modifier = Modifier.fillMaxWidth()
        )

        if (name.isNotBlank() && description.isNotBlank() && link.isNotBlank()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Приложение сгенерит QR код для вашей ссылки, которым Вы можете делиться с людьми",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
//TODO - убрать возможность создавать карточку пустой, нашел этот баг
        BigButton(
            text = "Сохранить",
            onClick = {
                onSave(
                    ProfileData(
                        name = name,
                        description = description,
                        link = link,
                        photoUri = photoUri?.toString()
                    )
                )
            }
        )
    }
}
