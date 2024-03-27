package cat.dam.dishdiscovery.objects

import cat.dam.dishdiscovery.objects.MealType

data class User(
    val administrator: Boolean = false,
    val likedDishes: List<Dish> = listOf(),
    val mealPlanner: List<MealType> = listOf(),
    val premium: Boolean = false,
    val publishedDishes: List<Dish> = listOf(),
    val userName: String = "",
    val uuid: String = ""
) {
    fun userToMap(): Map<String, Any?> {
        return mapOf(
            "UUID" to uuid,
            "UserName" to userName,
            "Administrator" to administrator,
            "LikedDishes" to likedDishes,
            "MealPlanner" to mealPlanner,
            "Premium" to premium,
            "PublishedDishes" to publishedDishes
        )
    }
}