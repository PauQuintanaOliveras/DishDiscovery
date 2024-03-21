package cat.dam.dishdiscovery.objects

data class Diet(
    val dietName: String,
) {
    fun dietToMap(): Map<String, Any?> {
        return mapOf("DietName" to dietName)
    }
}