package cat.dam.dishdiscovery.objects

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
}