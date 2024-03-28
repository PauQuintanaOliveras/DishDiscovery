package cat.dam.dishdiscovery.objects

import com.google.firebase.firestore.PropertyName

data class DishHeader(
    @PropertyName("Diets")
    val diets: List<Diet> = listOf(),
    @PropertyName("Dish")
    val dish: Dish,
    @PropertyName("DishAuthor")
    val dishAuthor: User = User(),
    @PropertyName("DishDescription")
    val dishDescription: String = "",
    @PropertyName("DishName")
    val dishName: String = "",
    @PropertyName("Image")
    val image: String = "",
    @PropertyName("MealType")
    val mealType: List<MealType> = listOf(),
    @PropertyName("Premium")
    val premium: Boolean = false,
    @PropertyName("Published")
    val published: Boolean = false,
    @PropertyName("Tags")
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
