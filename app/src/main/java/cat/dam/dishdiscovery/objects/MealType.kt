package cat.dam.dishdiscovery.objects

import com.google.firebase.firestore.PropertyName

data class MealType(
    @PropertyName("MealTypeName")
    val mealTypeName: String = ""
) {
    fun mealTypeToMap(): Map<String, Any?> {
        return mapOf(
            "MealTypeName" to mealTypeName
        )
    }
}
