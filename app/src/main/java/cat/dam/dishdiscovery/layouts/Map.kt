package cat.dam.dishdiscovery.layouts

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import cat.dam.dishdiscovery.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(navController: NavController) {
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

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                                val markerIcon = getMarkerIconFromDrawable(context, supermarket.name.toLowerCase().replace(" ", "_"), 100, 100)
                                googleMap.addMarker(MarkerOptions().position(supermarket.location).title(supermarket.name).icon(markerIcon))
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.navigate("main_page") }) {
                    val painter = painterResource(id = R.drawable.preferits)
                    Icon(painter = painter, contentDescription = "First Icon")
                }
                IconButton(onClick = { navController.navigate("main_page") }) {
                    val painter = painterResource(id = R.drawable.descobrir)
                    Icon(painter = painter, contentDescription = "Second Icon")
                }
                IconButton(onClick = { navController.navigate("map") }) {
                    val painter = painterResource(id = R.drawable.botiga)
                    Icon(painter = painter, contentDescription = "Third Icon")
                }
            }
        }
    )
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

fun getMarkerIconFromDrawable(context: Context, drawableName: String, width: Int, height: Int): BitmapDescriptor {
    val drawableId = context.resources.getIdentifier(drawableName, "drawable", context.packageName)
    if (drawableId != 0) {
        val drawable = ContextCompat.getDrawable(context, drawableId)
        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(drawable?.intrinsicWidth ?: 0, drawable?.intrinsicHeight ?: 0, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable?.draw(canvas)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        return BitmapDescriptorFactory.fromBitmap(resizedBitmap)
    } else {
        // Si no se encuentra el recurso gráfico, se devuelve un icono de marcador predeterminado
        return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
    }
}