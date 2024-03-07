package cat.dam.dishdiscovery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.dam.dishdiscovery.layouts.CreateRecipe
import cat.dam.dishdiscovery.layouts.LogInScreen
import cat.dam.dishdiscovery.layouts.Preferits
import cat.dam.dishdiscovery.layouts.RecoverPassword
import cat.dam.dishdiscovery.layouts.SignIn
import cat.dam.dishdiscovery.layouts.ViewRecipeScreen
import cat.dam.dishdiscovery.ui.theme.DishDiscoveryTheme
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
           val startDestination = if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) "main_page" else "main_page" // If the user is not logged in, the start destination is the login screen
            DishDiscoveryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(navController, startDestination = startDestination) {
                        composable("login_screen") { LogInScreen(navController) }
                        composable("sign_in_screen") { SignIn(navController) }
                        composable("recover_password_screen") { RecoverPassword() }
                        composable("main_page") { Preferits(navController) }
                        composable("view_recipe_screen") { ViewRecipeScreen() }
                        composable("create_recipe") { CreateRecipe() }
                    }
                }
            }
        }
    }
}








@Composable
fun MapScreen() {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle(context)
    var searchText by remember { mutableStateOf("") }
    data class Supermarket(val name: String, val location: LatLng)
    val supermarkets = listOf(

        Supermarket("mercadona", LatLng(41.96540599627968, 2.8115817385421895)),
        Supermarket("mercadona", LatLng(41.96921586429298, 2.783664491233071)),
        Supermarket("spar", LatLng(42.11785254564711, 2.76323113769396)),
        Supermarket("bonarea", LatLng(42.13622150672607, 2.7653458675980644)),
        Supermarket("lidl", LatLng(42.13413814465142, 2.774933356610811)),
        Supermarket("mercadona", LatLng(42.1143974396487, 2.77564919536588)),
        Supermarket("caprabo", LatLng(42.11248757372361, 2.7724740530379743)),
        Supermarket("condis", LatLng(42.119750721614174, 2.7741603776861865)),
        Supermarket("esclat", LatLng(42.113631518951216, 2.7740932214406135)),
        Supermarket("mercadona", LatLng(41.97433912059963, 2.787375011446554)),
        Supermarket("mercadona", LatLng(41.96869037876742, 2.807341144012495)),
        Supermarket("mercadona", LatLng(41.963957225813054, 2.8132768470182734)),
        Supermarket("mercadona", LatLng(41.99461898351478, 2.820552166896981)),
        Supermarket("mercadona", LatLng(41.99493131236733, 2.8182408170198103)),
        Supermarket("caprabo", LatLng(42.00234467021843, 2.824288455392485)),
        Supermarket("caprabo", LatLng(41.9845260386351, 2.8212161965912936)),
        Supermarket("caprabo", LatLng(41.97959795964362, 2.8083617117754907)),
        Supermarket("esclat", LatLng(42.00868325906437, 2.817380419171346)),
        Supermarket("esclat", LatLng(41.97721414289635, 2.7930362146431977)),
        Supermarket("esclat", LatLng(41.97094322902566, 2.8250456221133873)),
        Supermarket("esclat", LatLng(41.9831629000183, 2.813582793767925)),
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

        modifier = Modifier
    )
    Column(
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
    ) {
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Buscar") },
            modifier = Modifier
        )
                .padding(16.dp)
                .fillMaxWidth()
        TextField(
            .padding(16.dp)

        Box(
                .weight(1f)
                .fillMaxSize()
            AndroidView({ mapView }) { mapView ->
                MapsInitializer.initialize(context)
                    val initialLocation = LatLng(42.11849452583105, 2.7650268955548842)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 10f))
                        if (markerIcon != null) {
                        val markerIcon = getMarkerIconFromDrawable(context, context.resources.getIdentifier(supermarket.name, "drawable", context.packageName), 100, 100)

                    supermarkets.forEach { supermarket ->
                mapView.getMapAsync { googleMap ->
        ) {
            modifier = Modifier
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
            mapView.onPause()
        onDispose {
            mapView.onDestroy()
        }
    }

}
    return mapView
fun getMarkerIconFromDrawable(context: Context, drawableId: Int, width: Int, height: Int): BitmapDescriptor? {

        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val drawable = ContextCompat.getDrawable(context, drawableId)
    if (drawableId != 0) {
        val canvas = Canvas(bitmap)
        val bitmap = Bitmap.createBitmap(drawable?.intrinsicWidth ?: 0, drawable?.intrinsicHeight ?: 0, Bitmap.Config.ARGB_8888)
        drawable?.draw(canvas)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        return BitmapDescriptorFactory.fromBitmap(resizedBitmap)
    } else {
        // Log an error message or handle the case where the drawable resource was not found
        return null
}
    }