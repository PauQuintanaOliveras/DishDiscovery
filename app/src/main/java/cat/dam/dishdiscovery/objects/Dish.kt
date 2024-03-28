package cat.dam.dishdiscovery.objects

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cat.dam.dishdiscovery.Mesurement

data class Dish(
    val dishName: String = "",
    val dishDescription: String = "",
    val dishImageId: String? = null,
    val dishServings: Int = 0,
    val dishElaboration: String = "",
    val dishNotes: String = "",
    val dishVisibility: Boolean = false,
    val ingridientsQty: Map<Ingridient, Mesurement>
) {
    fun dishToMap(): Map<String, Any?> {
        return mapOf(
            "DishName" to dishName,
            "DishImageId" to dishImageId,
            "DishServings" to dishServings,
            "IngridientsQty" to ingridientsQty.values,
            "DishElaboration" to dishElaboration,
            "DishNotes" to dishNotes,
            "DishVisibility" to dishVisibility,
            //"DishUrl" to dishUrl
        )
    }
}
