package cat.dam.dishdiscovery.objects

data class Tag(
    val tagName: String,
    val dishHeaders: List<DishHeader>
) {
    fun tagToMap(): Map<String, Any?> {
        return mapOf(
            "TagName" to tagName,
            "DishHeaders" to dishHeaders
        )
    }
}
