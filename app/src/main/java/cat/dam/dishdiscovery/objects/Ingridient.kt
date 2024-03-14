package cat.dam.dishdiscovery.objects

data class Ingridient(
    val name: String,
) {
    fun ingridientToMap(): Map<String, Any?> {
        return mapOf(
            "Name" to name
        )
    }
}
