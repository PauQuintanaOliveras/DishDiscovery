package cat.dam.dishdiscovery.objects

import cat.dam.dishdiscovery.Mesurement
import com.google.firebase.firestore.PropertyName

data class Mesurement(
    @PropertyName("MesurementName")
    val mesurementName: String,
    val quantity: Float,
) {
    fun mesurementToMap(): Map<String, Any?> {
        return mapOf(
            "MesurementName" to mesurementName,
            "Quantity" to quantity
        )
    }

    fun toString(mesurementName: String, quantity: Float): Mesurement {
        return Mesurement(mesurementName, quantity)
    }
}