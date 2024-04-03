package cat.dam.dishdiscovery.objects

import com.google.firebase.firestore.PropertyName

data class Mesurement(
    @PropertyName("MesurementName")
    var mesurementName: String,
    var quantity: Float,
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