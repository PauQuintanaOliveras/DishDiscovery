package cat.dam.dishdiscovery.objects

data class MealType(
    val dishHeader: DishHeader,
    val mealTypeName: String,
) {
    fun mealTypeToMap(): Map<String, Any?> {
        return mapOf(
            "DishHeader" to dishHeader,
            "MealTypeName" to mealTypeName
        )
    }
}
