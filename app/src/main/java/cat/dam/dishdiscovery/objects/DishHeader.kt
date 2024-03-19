package cat.dam.dishdiscovery.objects


data class DishHeader(
    val diets: List<Diet>,
    val dish: Dish,
    val dishAuthor: User,
    val dishDescription: String,
    val dishName: String,
    val image: String,
    val mealType: MealType,
    val premium: Boolean,
    val published: Boolean,
    val tags: List<Tag>
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
