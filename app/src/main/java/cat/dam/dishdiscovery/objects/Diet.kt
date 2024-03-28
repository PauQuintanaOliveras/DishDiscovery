package cat.dam.dishdiscovery.objects

import com.google.firebase.firestore.PropertyName

data class Diet(
    @PropertyName("DietName")
    val dietName: String = "",
) {
    fun dietToMap(): Map<String, Any?> {
        return mapOf("DietName" to dietName)
    }
}