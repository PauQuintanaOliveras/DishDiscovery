package cat.dam.dishdiscovery.objects

data class Mesurement(
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