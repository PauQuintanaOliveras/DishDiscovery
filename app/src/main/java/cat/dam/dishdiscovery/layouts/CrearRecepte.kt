@file:OptIn(ExperimentalMaterial3Api::class)

package cat.dam.dishdiscovery.layouts

import android.annotation.SuppressLint
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import cat.dam.dishdiscovery.Mesurement
import cat.dam.dishdiscovery.R
import cat.dam.dishdiscovery.objects.Dish
import cat.dam.dishdiscovery.objects.Ingridient
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.FirebaseFirestore


@Preview
@Composable
fun CreateRecipe() {
    val context = LocalContext.current
    val dishName = remember { mutableStateOf("") }
    val dishElaboration = remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    var dishServings by remember { mutableIntStateOf(0) }


    val selectImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedImageUri = uri
        }

    val takePictureLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            if (bitmap != null) {
                val filename = System.currentTimeMillis().toString()
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }
                }

                val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                val uri = context.contentResolver.insert(contentUri, contentValues)

                if (uri != null) {
                    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }
                    selectedImageUri = uri
                } else {
                    Toast.makeText(context, "Failed to save picture", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Failed to take picture", Toast.LENGTH_LONG).show()
            }
        }

    val requestCameraPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                val filename = System.currentTimeMillis().toString()
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                        put(MediaStore.Images.Media.IS_PENDING, 1)
                    }
                }

                val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                imageUri.value = context.contentResolver.insert(contentUri, contentValues)

                if (isGranted) {
                    takePictureLauncher.launch(null)
                }
            } else {
                Toast.makeText(
                    context,
                    "Camera permission is required to take pictures",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    val requestWriteExternalStoragePermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, you can write to external storage here
            } else {
                Toast.makeText(
                    context,
                    "Write external storage permission is required",
                    Toast.LENGTH_LONG
                ).show()
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
            value = dishName.value,
            onValueChange = { newValue ->
                if (!newValue.contains("\n")) {
                    dishName.value = newValue
                }
            },
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
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { /* Handle action */ })

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
                painter = painterResource(id = R.drawable.recuadre),
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
                Text(
                    text = "Galeria",
                    onTextLayout = {}
                )

            }

            Button(
                onClick = {
                    requestWriteExternalStoragePermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                },
            ) {
                Text(
                    text = "Càmera",
                    onTextLayout = {}
                )
            }
        }
        Spacer(modifier = Modifier.height(11.dp))
        Text(
            text = "Per Quantes Persones",
            fontSize = 15.sp,
            onTextLayout = {},
            modifier = Modifier.padding(5.dp)
        )

        dishServings = showNumberPicker()

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
            modifier = Modifier.padding(5.dp)
        )

        Spacer(modifier = Modifier.height(11.dp))
        TextField( // Add this TextField
            value = dishElaboration.value,
            onValueChange = { dishElaboration.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clip(RoundedCornerShape(100.dp)),
            label = {
                Text(
                    text = "",
                    fontSize = 10.sp,
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
            value = dishElaboration.value,
            onValueChange = { dishElaboration.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clip(RoundedCornerShape(100.dp)),
            label = {
                Text(
                    text = "",
                    fontSize = 10.sp,
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
            onClick = { uploadDish(dishName, dishServings) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(200.dp, 50.dp)


        ) {
            Text(
                text = "Fet",
                fontSize = 10.sp
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchbar(
    searchQuery: String,
    searchResult: List<Ingridient>,
    onSearchQueryChange: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    Scaffold(

    ) {
        SearchBar(
            query = text,
            onQueryChange ={text = it},
            onSearch = {active = false},
            active = active,
            onActiveChange = {active = it},
            placeholder = { Text("Cerca un ingredient") },
            trailingIcon = {}
        ) {

        }
    }
}

@Composable
fun showNumberPicker(): Int {
    var tempNumberPickerValue by remember { mutableIntStateOf(1) }
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            NumberPicker(context).apply {
                minValue = 1
                maxValue = 5
                setOnValueChangedListener { _, _, newVal ->
                    tempNumberPickerValue = newVal
                }
            }
        }
    )
    return tempNumberPickerValue
}
fun uploadDish(dishElaboration: MutableState<String>, dishServings: Int) {
    val TAG = "CreateRecipe"
    val dish = Dish(
        dishElaboration,
        dishServings,
        mapOf<Ingridient, Mesurement>(ingridient to mesurement)
    ).dishToMap()

    FirebaseFirestore.getInstance().collection("Dish").add(dish)
        .addOnSuccessListener {
            Log.d(TAG, "dish $dish added")
        }
        .addOnFailureListener {
            Log.d(TAG, "dish $dish not added")

        }
}
