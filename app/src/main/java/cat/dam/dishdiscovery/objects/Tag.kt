package cat.dam.dishdiscovery.objects

data class Tag(
    val tagName: String,
) {
    fun tagToMap(): Map<String, Any?> {
        return mapOf(
            "TagName" to tagName,
        )
    }
}
