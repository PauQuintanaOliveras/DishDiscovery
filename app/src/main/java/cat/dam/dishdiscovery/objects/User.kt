package cat.dam.dishdiscovery.objects

data class User(
    val uuid: String?,
    val userName: String,
    val administrator: Boolean,
    val likedDishes: List<String>,
    val mealPlanner: String,
    val premium: Boolean,
    val publishedDishes: List<String>,
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
