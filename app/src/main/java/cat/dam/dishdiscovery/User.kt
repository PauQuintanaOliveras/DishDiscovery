package cat.dam.dishdiscovery

data class User(
    val id: String?,
    val userName: String,
    val administrator: Boolean,
    val likedDishes: List<String>,
    val mealPlanner: String,
    val premium: Boolean,
    val publishedDishes: List<String>,
) {
    fun userToMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "userName" to userName,
            "administrator" to administrator,
            "likedDishes" to likedDishes,
            "mealPlanner" to mealPlanner,
            "premium" to premium,
            "publishedDishes" to publishedDishes
        )
    }
}
