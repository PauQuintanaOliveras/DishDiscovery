package cat.dam.dishdiscovery.objects

data class Diet(
    val dietName: String,
    val dishHeaders: List<String>,
) {
    fun dietToMap(): Map<String, Any?> {
        return mapOf(
            "DietName" to dietName,
            "DishHeaders" to dishHeaders
        )
    }
}