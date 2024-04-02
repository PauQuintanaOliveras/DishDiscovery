package cat.dam.dishdiscovery.objects

import cat.dam.dishdiscovery.Mesurement
import com.google.firebase.firestore.PropertyName

data class Dish(
    @PropertyName("DishName")
    val dishName: String = "",
    @PropertyName("DishNameCat")
    val dishNameCat: String = "",
    @PropertyName("DishNameEsp")
    val dishNameEsp: String = "",
    @PropertyName("DishDescription")
    val dishDescription: String = "",
    @PropertyName("DishDescriptionCat")
    val dishDescriptionCat: String = "",
    @PropertyName("DishDescriptionEsp")
    val dishDescriptionEsp: String = "",
    @PropertyName("DishImage")
    val dishImageId: String = "",
    @PropertyName("DishServings")
    val dishServings: Int = 0,
    @PropertyName("DishElaboration")
    val dishElaboration: String = "",
    @PropertyName("DishNotes")
    val dishNotes: String = "",
    @PropertyName("DishVisibility")
    val dishVisibility: Boolean = false,
    @PropertyName("DishUrl")
    val ingridientsQty: Map<Ingridient, Mesurement> = mapOf(Ingridient("empty") to Mesurement("empty", 0f)),

) {
    fun dishToMap(): Map<String, Any?> {
        return mapOf(
            "DishName" to dishName,
            "DishNameCat" to dishNameCat,
            "DishNameEsp" to dishNameEsp,
            "DishImage" to dishImageId,
            "DishServings" to dishServings,
            "DishElaboration" to dishElaboration,
            "DishNotes" to dishNotes,
            "DishVisibility" to dishVisibility,
            "DishDescription" to dishDescription,
            "DishDescriptionCat" to dishDescription,
            "DishDescriptionEsp" to dishDescriptionEsp,
        )
    }
}
