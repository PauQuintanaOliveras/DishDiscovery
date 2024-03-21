package cat.dam.dishdiscovery.objects

data class MealType(
    val mealTypeName: String,
) {
    fun mealTypeToMap(): Map<String, Any?> {
        return mapOf(
            "MealTypeName" to mealTypeName
        )
    }
}
