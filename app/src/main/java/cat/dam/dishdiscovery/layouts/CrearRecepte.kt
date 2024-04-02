@file:OptIn(ExperimentalMaterial3Api::class)

package cat.dam.dishdiscovery.layouts

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.dam.dishdiscovery.CreateRecipeViewModel
import cat.dam.dishdiscovery.Mesurement
import cat.dam.dishdiscovery.objects.Dish
import cat.dam.dishdiscovery.objects.Ingridient
import cat.dam.dishdiscovery.searchbar
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CreateRecipe() {
    val context = LocalContext.current
    var dishName by remember { mutableStateOf("") }
    var dishElaboration by remember { mutableStateOf("") }
    var dishImage by remember { mutableStateOf<Uri?>(null) }
    var dishImageId = ""
    var dishServings by remember { mutableIntStateOf(0) }
    var dishNotes by remember { mutableStateOf("") }
    var dishVisibility by remember { mutableStateOf(false) }
    var dishIngridients by remember { mutableStateOf<Map<Ingridient, Mesurement>>(mapOf()) }
    val focusManager = LocalFocusManager.current
    var authorNotes by remember { mutableStateOf("") }
    val selectImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            dishImage = uri
        }

    val takePictureLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            if (bitmap != null) {
                dishImageId = System.currentTimeMillis().toString()
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, dishImageId)
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
                    dishImage = uri
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
                takePictureLauncher.launch(null)
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
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear Recepte",
            fontSize = 24.sp,
            onTextLayout = {}
        )
        TextField(
            value = dishName,
            onValueChange = { it:String ->
                if (!it.contains("\n")) {
                    dishName = it
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clip(
                    RoundedCornerShape(100.dp),
                ),
            label = {
                Text(
                    text = "Nom de la Recepta",
                    fontSize = 15.sp,
                    onTextLayout = {}
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()})
        )
        Text(
            text = "Imatge de la Recepta",
            fontSize = 15.sp,
            onTextLayout = {},
            modifier = Modifier.padding(5.dp)
        )
        if (dishImage != null) {
            Image(
                painter = rememberImagePainter(data = dishImage),
                contentDescription = "User's recipe image",
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Per Quantes Persones",
                fontSize = 15.sp,
                onTextLayout = {},
                modifier = Modifier.padding(5.dp)
            )
            Spacer(modifier = Modifier.width(26.dp))
            dishServings = showNumberPicker()
        }
        var vm = viewModel { CreateRecipeViewModel() }
        vm.ingMes[Ingridient(searchbar())] = Mesurement("empty", 0.0f)
        // Text(text =text
       LazyColumn(modifier = Modifier
           .fillMaxWidth()
           .heightIn(max = 30000.dp)){
            item{
                vm.ingMes.forEach { it ->
                    if (it.key.name.isNotBlank()) {
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                            Text(modifier = Modifier.weight(2f), text = it.key.name)
                            TextField(
                                modifier = Modifier.weight(0.5f),
                                singleLine = true,
                                value = "qty",
                                onValueChange = { qty -> it.value.amount = qty.toFloat()},
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            TextField(
                                modifier = Modifier.weight(0.5f),
                                singleLine = true,
                                value = "mesurement",
                                onValueChange = { mes -> it.value.name = mes },
                            )
                        }
                    }
                }
            }
       }
        TextField( // Add this TextField
            value = dishElaboration,
            onValueChange = { dishElaboration = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clip(RoundedCornerShape(100.dp)),
            label = {
                Text(
                    text = "Els Passos de la Recepta",
                    fontSize = 15.sp,
                )
            }
        )
        TextField( // Add this TextField
            value = authorNotes,
            onValueChange = { authorNotes = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clip(RoundedCornerShape(100.dp)),
            label = {
                Text(
                    text = "Author Notes",
                    fontSize = 15.sp,
                )
            }
        )

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
            onClick = {
                uploadDish(
                    dishName,
                    dishImageId,
                    dishDescription = "",
                    dishImage,
                    dishServings,
                    dishIngridients,
                    dishElaboration,
                    dishNotes,
                    dishVisibility
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(200.dp, 50.dp)
        ) {
            Text(
                text ="Fet",
                fontSize = 10.sp
            )
        }
    }
}


@Composable
fun showNumberPicker(): Int {
    var tempNumberPickerValue by remember { mutableIntStateOf(1) }
    AndroidView(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        factory = { context ->
            NumberPicker(context).apply {
                minValue = 1
                maxValue = 99
                setOnValueChangedListener { _, _, newVal ->
                    tempNumberPickerValue = newVal
                }
            }
        }
    )
    return tempNumberPickerValue
}

fun uploadDish(
    dishName: String,
    dishImageId: String,
    dishDescription: String,
    dishImage: Uri?,
    dishServings: Int,
    ingridientsQty: Map<Ingridient, Mesurement>,
    dishElaboration: String,
    dishNotes: String,
    dishVisibility: Boolean
) {
    val TAG = "CreateRecipe"
    val dish = Dish(
        dishId = "",
        dishName,
        dishImageId,
        dishDescription,
        dishServings,
        dishElaboration,
        dishNotes,
        dishVisibility,
        ingridientsQty,
    ).dishToMap()

    FirebaseFirestore.getInstance().collection("Dish").add(dish)
        .addOnSuccessListener {
            Log.d(TAG, "dish $dish added")
        }
        .addOnFailureListener {
            Log.d(TAG, "dish $dish not added")

        }

    FirebaseStorage.getInstance()
        .getReference("DishImages")
        .child(dishImageId!!)
        .putFile(dishImage!!)
        .addOnSuccessListener {
            Log.d(TAG, "Image $dishImageId uploaded")
        }
        .addOnFailureListener {
            Log.d(TAG, "Image $dishImageId not uploaded")
        }
}
