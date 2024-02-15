package cat.dam.dishdiscovery

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun MapViewContainer() {
    val context = LocalContext.current
    val mapView = MapView(context)
    mapView.onCreate(null)
    mapView.onResume()

    val rememberedMapView = remember { mapView }

    AndroidView({ rememberedMapView }) { mapView ->
        mapView.getMapAsync { googleMap ->
            setMapLocation(googleMap, context)
        }
    }
}

fun setMapLocation(googleMap: GoogleMap, context: Context) {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val currentLocation = LatLng(location.latitude, location.longitude)
                googleMap.addMarker(MarkerOptions().position(currentLocation).title("Mi ubicación"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
            }
        }
    }
}