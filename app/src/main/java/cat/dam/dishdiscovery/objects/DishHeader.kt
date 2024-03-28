package cat.dam.dishdiscovery.objects

data class DishHeader(
    val diets: List<Diet> = listOf(),
    val dish: Dish,
    val dishAuthor: User = User(),
    val dishDescription: String = "",
    val dishName: String = "",
    val image: String = "",
    val mealType: List<MealType> = listOf(),
    val premium: Boolean = false,
    val published: Boolean = false,
    val tags: List<Tag> = listOf()
) {
    fun dishHeaderToMap(): Map<String, Any?> {
        return mapOf(
            "Diets" to diets,
            "Dish" to dish,
            "DishAuthor" to dishAuthor,
            "DishDescription" to dishDescription,
            "DishName" to dishName,
            "Image" to image,
            "MealType" to mealType,
            "Premium" to premium,
            "Published" to published,
            "Tags" to tags
        )
    }
}
