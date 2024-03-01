package cat.dam.dishdiscovery
import android.content.ContentValues
import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.NumberPicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter
import java.io.ByteArrayOutputStream

class CrearRecepte {



    @Preview

    @Composable
    fun DisplayText() {
        val context = LocalContext.current
        val textState = remember { mutableStateOf("") }
        val productState = remember { mutableStateOf("") }
        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

        val selectImageLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedImageUri = uri}

        val requestCameraPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {

            } else {
                // Permission has not been granted, show a message to the user explaining why the operation can't be performed
            }
        }
        val imageUri = remember { mutableStateOf<Uri?>(null) }
        val takePictureLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { success ->
            if (success) {

                selectedImageUri = imageUri.value
            } else {

            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())

        ) {
            Text(
                text = "Crear Recepte",
                fontSize = 24.sp,
                onTextLayout = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Titol Recepte",
                fontSize = 15.sp,
                onTextLayout = {},
                modifier = Modifier.padding(5.dp)
            )
            Spacer(modifier = Modifier.height(11.dp))
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clip(RoundedCornerShape(100.dp)),

                label = {
                    Text(
                        text = "",
                        fontSize = 10.sp,
                        onTextLayout = {}
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Imatge de la Recepta",
                fontSize = 15.sp,
                onTextLayout = {},
                modifier = Modifier.padding(5.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (selectedImageUri != null) {
                Image(
                    painter = rememberImagePainter(data = selectedImageUri),
                    contentDescription = "User's recipe image",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.testimage),
                    contentDescription = "Placeholder image",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { selectImageLauncher.launch("image/*") }
                ) {
                    Text(text = "Galeria")
                }

                Button(
                    onClick = {
                        requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                        val filename = System.currentTimeMillis().toString()
                        val contentValues = ContentValues().apply {
                            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { //this one
                                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                                put(MediaStore.Images.Media.IS_PENDING, 1)
                            }
                        }

                        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        imageUri.value = context.contentResolver.insert(contentUri, contentValues)

                        if (imageUri.value != null) {
                            takePictureLauncher.launch(imageUri.value)
                        }
                    },

                    ) {
                    Text(text = "Càmera")
                }
            }
            Spacer(modifier =Modifier.height(11.dp))
            Text(
                text = "Per Quantes Persones",
                fontSize = 15.sp,
                onTextLayout = {},
                modifier = Modifier.padding(5.dp)
            )
            ShowNumberPicker()

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Afegir Ingredients",
                fontSize = 15.sp,
                onTextLayout = {},
                modifier = Modifier.padding(5.dp)
            )
            Spacer(modifier = Modifier.height(11.dp))
            Image(
                painter = painterResource(id = R.drawable.ingredients),
                contentDescription = "Imagen de ingredientes",
                modifier = Modifier
                    .padding(5.dp)
                    .height(100.dp)
                    .fillMaxWidth()
                    .clickable { /* Aquí va el código para manejar el clic en la imagen */ }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Instruccions dels Ingredients (opcional)",
                fontSize = 15.sp,
                onTextLayout = {},
                modifier = Modifier.padding(5.dp)
            )

            Spacer(modifier = Modifier.height(11.dp))
            TextField( // Add this TextField
                value = productState.value,
                onValueChange = { productState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clip(RoundedCornerShape(100.dp)),
                label = {
                    Text(
                        text = "",
                        fontSize = 10.sp,
                        onTextLayout = {}
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Notes de l'autor (opcional)",
                fontSize = 15.sp,
                onTextLayout = {},
                modifier = Modifier.padding(5.dp)
            )
            Spacer(modifier = Modifier.height(11.dp))
            TextField( // Add this TextField
                value = productState.value,
                onValueChange = { productState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clip(RoundedCornerShape(100.dp)),
                label = {
                    Text(
                        text = "",
                        fontSize = 10.sp,
                        onTextLayout = {}
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            var selectedOption by remember { mutableStateOf("Private") }
            val options = listOf("Private", "Public")

            Column {
                options.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = { selectedOption = text }
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge.merge(),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
            Button(
                onClick = { /* Handle button click */ },
                modifier= Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp, 50.dp)


            ) {
                Text( text = "Fet",
                    fontSize = 10.sp,
                    onTextLayout = {})
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShowNumberPicker() {
        var selectedValue by remember { mutableStateOf(0) }

        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                NumberPicker(context).apply {
                    minValue = 1
                    maxValue = 5
                    setOnValueChangedListener { _, _, newVal ->
                        selectedValue = newVal
                    }
                }
            }
        )
    }

}