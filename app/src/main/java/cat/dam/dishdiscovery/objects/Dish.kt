package cat.dam.dishdiscovery.objects

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName


data class Dish(
    @DocumentId
    val dishId: String = "",
    @PropertyName("DishName")
    val dishName: String = "",
    @PropertyName("DishNameCat")
    val dishNameCat: String = "",
    @PropertyName("DishNameEsp")
    val dishNameEsp: String = "",
    @PropertyName("DishImage")
    val dishImageId: String = "",
    @PropertyName("DishDescription")
    val dishDescription: String = "",
    @PropertyName("DishDescriptionCat")
    val dishDescriptionCat: String = "",
    @PropertyName("DishDescriptionEsp")
    val dishDescriptionEsp: String = "",
    @PropertyName("DishServings")
    val dishServings: Float = 0f,
    @PropertyName("DishElaboration")
    val dishElaboration: String = "",
    @PropertyName("DishNotes")
    val dishNotes: String = "",
    @PropertyName("DishVisibility")
    val dishVisibility: Boolean = false,

    val ingridientsQty: MutableMap<Ingridient,Mesurement> = mutableMapOf(),

    )

{
    fun dishToMap(): Map<String, Any?> {
        return mapOf(
            "DishName" to dishName,
            "DishNameCat" to dishNameCat,
            "DishNameEsp" to dishNameEsp,
            "DishImage" to dishImageId,
            "DishServings" to dishServings,
            "IngridientsQty" to ingridientsQty,
            "DishElaboration" to dishElaboration,
            "DishNotes" to dishNotes,
            "DishVisibility" to dishVisibility,
            "DishDescription" to dishDescription,
            "DishDescriptionEsp" to dishDescriptionEsp,
        )
    }
}