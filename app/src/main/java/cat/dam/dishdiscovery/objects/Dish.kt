package cat.dam.dishdiscovery.objects

import cat.dam.dishdiscovery.Mesurement

data class Dish(
    val dishElaboration: String,
    val dishServings: Int,
    val dishUrl: String,
    val ingridientsQty: Map<Ingridient, Mesurement>
) {
    fun dishToMap(): Map<String, Any?> {
        return mapOf(
            "DishElaboration" to dishElaboration,
            "DishServings" to dishServings,
            "DishUrl" to dishUrl,
            "IngridientsQty" to ingridientsQty
        )
    }
}
