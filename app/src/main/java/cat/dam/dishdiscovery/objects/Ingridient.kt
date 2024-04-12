package cat.dam.dishdiscovery.objects

import com.google.firebase.firestore.PropertyName

data class Ingridient(
    @PropertyName("IngridientName")
    val name: String,
) {
    fun ingridientToMap(): Map<String, Any?> {
        return mapOf(
            "Name" to name
        )
    }
}
