package cat.dam.dishdiscovery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.dam.dishdiscovery.ui.theme.DishDiscoveryTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            DishDiscoveryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(navController, startDestination = "map_screen") {
                        composable("map_screen") { MapScreen() }
                    }
                }
            }
        }
    }
}
@Composable
fun LoginScreen(navController: NavController) {
    val username = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val logo = painterResource(id = R.drawable.logo)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun isValidInput(input: String): Boolean {
        val pattern = Regex("^[a-zA-Z0-9_]*$")
        return pattern.matches(input)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = logo,
            contentDescription = "Logo",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Usuari") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Contrasenya") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Has oblidat la contrasenya?",
            modifier = Modifier.clickable { navController.navigate("recover_password_screen") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (!isValidInput(username.value.text) || !isValidInput(password.value.text)) {
                // Muestra un mensaje de error
                scope.launch {
                    snackbarHostState.showSnackbar("L'usuari només pot accedir si afegeix a-z, A-Z, 0-9 o _")
                }
            } else {
                // Continúa amb l' inici de sessio
            }
        }) {
            Text("Accedir")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Registrar-se",
            modifier = Modifier.clickable { navController.navigate("sign_in_screen") })
    }
    SnackbarHost(hostState = snackbarHostState)
}


@Composable
fun RecoverPassword() {
    var username by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun isValidInput(input: String): Boolean {
        val pattern = Regex("^[a-zA-Z0-9_]*$")
        return pattern.matches(input)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nom d'Usuari") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("Nova Contrasenya") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = { Text("Repeteix la Contrasenya") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (!isValidInput(username) || !isValidInput(newPassword) || !isValidInput(repeatPassword)) {
                //Posa un missatge d'error
                scope.launch {
                    snackbarHostState.showSnackbar("L'usuari només pot accedir si afegeix a-z, A-Z, 0-9 o _")
                }
            } else {
                // Aquí va la lógica de confirmació
            }
        }) {
            Text("Confirmar")
        }
    }
    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun SignIn() {
    val username = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun isValidInput(input: String): Boolean {
        val pattern = Regex("^[a-zA-Z0-9_]*$")
        return pattern.matches(input)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("nom Usuari") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Correu Electrònic") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Contrasenya") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (!isValidInput(username.value.text) || !isValidInput(password.value.text)) {
                // Muestra un mensaje de error
                scope.launch {
                    snackbarHostState.showSnackbar("L'usuari només pot accedir si afegeix a-z, A-Z, 0-9 o _")
                }
            } else {
                // Continúa amb l' inici de sessio
            }
        }) {
            Text("Registrar-se")
        }
    }
    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun MapScreen() {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle(context)
    var searchText by remember { mutableStateOf("") }
    data class Supermarket(val name: String, val location: LatLng)

    val supermarkets = listOf(
        Supermarket("bonarea", LatLng(42.13622150672607, 2.7653458675980644)),
        Supermarket("spar", LatLng(42.11785254564711, 2.76323113769396)),
        Supermarket("mercadona", LatLng(42.1143974396487, 2.77564919536588)),
        Supermarket("lidl", LatLng(42.13413814465142, 2.774933356610811)),
        Supermarket("caprabo", LatLng(42.11248757372361, 2.7724740530379743)),
        Supermarket("condis", LatLng(42.119750721614174, 2.7741603776861865)),
        Supermarket("esclat", LatLng(42.113631518951216, 2.7740932214406135)),
        Supermarket("mercadona", LatLng(41.97433912059963, 2.787375011446554)),
        Supermarket("mercadona", LatLng(41.96921586429298, 2.783664491233071)),
        Supermarket("mercadona", LatLng(41.96869037876742, 2.807341144012495)),
        Supermarket("mercadona", LatLng(41.96540599627968, 2.8115817385421895)),
        Supermarket("mercadona", LatLng(41.963957225813054, 2.8132768470182734)),
        Supermarket("mercadona", LatLng(41.99461898351478, 2.820552166896981)),
        Supermarket("mercadona", LatLng(41.99493131236733, 2.8182408170198103)),
        Supermarket("caprabo", LatLng(42.00234467021843, 2.824288455392485)),
        Supermarket("caprabo", LatLng(41.9845260386351, 2.8212161965912936)),
        Supermarket("caprabo", LatLng(41.97959795964362, 2.8083617117754907)),
        Supermarket("esclat", LatLng(42.00868325906437, 2.817380419171346)),
        Supermarket("esclat", LatLng(41.97721414289635, 2.7930362146431977)),
        Supermarket("esclat", LatLng(41.9831629000183, 2.813582793767925)),
        Supermarket("esclat", LatLng(41.97094322902566, 2.8250456221133873)),
        Supermarket("esclat", LatLng(41.96900356655059, 2.8172904527205143)),
        Supermarket("esclat", LatLng(41.9599461352336, 2.801115802638078)),
        Supermarket("esclat", LatLng(41.95405741444401, 2.8103914186737593)),
        Supermarket("lidl", LatLng(41.9893173031474, 2.807083564868157)),
        Supermarket("lidl", LatLng(41.97171922254533, 2.809393155861726)),
        Supermarket("aldi", LatLng(41.98517861493366, 2.8100031148924796)),
        Supermarket("aldi", LatLng(41.96947733841349, 2.805633329621291)),
        Supermarket("consum", LatLng(41.983236899120705, 2.822650177964668)),
        Supermarket("consum", LatLng(41.969964818517525, 2.817500336635358)),
        Supermarket("consum", LatLng(41.976050591540165, 2.7841229248191266)),
        Supermarket("dia", LatLng(41.97843582468891, 2.8165861711303424)),
        Supermarket("dia", LatLng(41.974096852447246, 2.8234526262360893))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Buscar") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            AndroidView({ mapView }) { mapView ->
                MapsInitializer.initialize(context)
                mapView.getMapAsync { googleMap ->
                    val initialLocation = LatLng(42.11849452583105, 2.7650268955548842)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 10f))

                    supermarkets.forEach { supermarket ->
                        val markerIcon = getMarkerIconFromDrawable(context, context.resources.getIdentifier(supermarket.name, "drawable", context.packageName), 100, 100)
                        if (markerIcon != null) {
                            googleMap.addMarker(MarkerOptions().position(supermarket.location).title(supermarket.name).icon(markerIcon))
                        } else {
                            // Log an error message or handle the case where the marker icon was not found
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(context: Context): MapView {
    val mapView = remember {
        MapView(context).apply {
            onCreate(Bundle())
            onResume()
        }
    }

    DisposableEffect(mapView) {
        onDispose {
            mapView.onPause()
            mapView.onDestroy()
        }
    }

    return mapView
}

fun getMarkerIconFromDrawable(context: Context, drawableId: Int, width: Int, height: Int): BitmapDescriptor? {
    if (drawableId != 0) {
        val drawable = ContextCompat.getDrawable(context, drawableId)
        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(drawable?.intrinsicWidth ?: 0, drawable?.intrinsicHeight ?: 0, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable?.draw(canvas)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        return BitmapDescriptorFactory.fromBitmap(resizedBitmap)
    } else {
        // Log an error message or handle the case where the drawable resource was not found
        return null
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    DishDiscoveryTheme {
        LoginScreen(navController)
    }
}





