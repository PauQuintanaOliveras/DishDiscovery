package cat.dam.dishdiscovery.objects

import com.google.firebase.firestore.PropertyName

data class Tag(
    @PropertyName("TagName")
    var tagName: String = "",
) {
    fun tagToMap(): Map<String, Any?> {
        return mapOf("TagName" to tagName)
    }
}
