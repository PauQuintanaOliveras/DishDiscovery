package cat.dam.dishdiscovery.layouts

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cat.dam.dishdiscovery.CreateRecipeViewModel
import cat.dam.dishdiscovery.objects.Ingridient
import cat.dam.dishdiscovery.Mesurement
import cat.dam.dishdiscovery.R
import cat.dam.dishdiscovery.objects.Dish
import cat.dam.dishdiscovery.objects.IngridientQty
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log


@Composable
fun ViewRecipeScreen(dishId: String?) {
    val boxSize = 100.dp
    val defaultPadding = 16.dp
    val db = FirebaseFirestore.getInstance()
    var dish by remember { mutableStateOf<Dish?>(null) }

    LaunchedEffect(dishId) {
        dishId?.let {
            val dishRef = db.collection("Dish").document(it)
                dishRef.get().addOnSuccessListener { document ->
                    if (document != null) {
                        dish = document.toObject(Dish::class.java)
                    } else {
                        Log.d("ViewRecipeScreen", "No document found with DishName: $it")
                    }
                }


            var ingridientsQty : IngridientQty? = null
            var ingridientsQtyRef = dishRef.collection("IngridientsQty")
            ingridientsQtyRef.get().addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        if(document != null) {
                            if (ingridientsQty != null) {
                                ingridientsQty.ingridient = document.id
                                ingridientsQty.mesurement = document.getDocumentReference("Mesurement")?.id ?: ""
                                ingridientsQty.qty = document.getDouble("Qty")?.toFloat() ?: 0f
                            }
                            Log.d("AAAAAAAAAAAAAPUTAPUTAPUTA", "IngridientsQty: ${document.data}")
                        }
                    }
                } else {
                    Log.d("ViewRecipeScreen", "No document found with DishName: $it")
                }
            }
            /*
                .get().addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        if(document != null) {
                            ingridientsQtyRef = document.toObject(IngridientQty::class.java)
                        }
                        //dish = dish?.copy(ingridientsQty = dish?.ingridientsQty?.plus(ingridient to mesurement) ?: mapOf(ingridient to mesurement))
                    }
                } else {
                    Log.d("ViewRecipeScreen", "No document found with DishName: $it")
                }
            }*/


            /*db.collection("Dish").whereEqualTo("DishName", it).get().addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        dish = document.toObject(Dish::class.java)
                    }
                } else {
                    Log.d("ViewRecipeScreen", "No document found with DishName: $it")
                }
            }
        } ?: run {
            Log.d("ViewRecipeScreen", "Dish name is null")
        }
        */
    }
}

        val dishServings = dish?.dishServings?.toFloat() ?: 1f
        var servings by remember { mutableFloatStateOf(dishServings) }
        val ingredients = dish?.ingridientsQty ?: emptyMap()

        //Must be after Ingridients added to the list
        var updatedIngredients by remember {
            mutableStateOf(
                updateMeasurements(
                    ingredients,
                    servings,
                    dishServings
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.sandwich),
                        contentDescription = "Dish Image",
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()

                    )
                    Spacer(
                        modifier = Modifier
                            .height(boxSize / 2)
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.background)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(boxSize)
                        .padding(defaultPadding)
                        .align(Alignment.BottomCenter)
                        .clip(RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp))
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(
                            text = "RECIPE",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = MaterialTheme.typography.bodySmall.fontWeight

                        )
                        Text(
                            text = dish?.dishName ?: "",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                        )
                        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Text(
                                text = "by ", modifier = Modifier.align(Alignment.CenterVertically),
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,

                                )
                            Text(
                                text = "Author",
                                modifier = Modifier.align(Alignment.CenterVertically),
                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                fontWeight = MaterialTheme.typography.bodySmall.fontWeight
                            )
                        }

                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(boxSize / 1.5f)
                    .background(color = MaterialTheme.colorScheme.background)
            )
            {
                //TODO: put icon and likes
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(boxSize * 1.5f)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Text(
                    text = dish?.dishDescription ?: "",
                    modifier = Modifier
                        .padding(defaultPadding)
                        .align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(boxSize * 1.25f)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Card(
                    modifier = Modifier
                        .padding(defaultPadding)
                        .align(Alignment.Center)
                        .fillMaxSize(),
                    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.background),
                    border = BorderStroke(1.dp, DarkGray)

                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "1hr 20Min",
                            modifier = Modifier.padding(defaultPadding),
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Black,

                            //fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                        )
                        Text(
                            text = "Total Time",
                            modifier = Modifier.padding(
                                bottom = defaultPadding
                            ),
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = FontWeight.Normal,
                            //fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(defaultPadding)
                    .height(boxSize / 2)
                    .background(Transparent) //MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    text = "Ingredients",
                    modifier = Modifier
                        .align(Alignment.CenterStart),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Black,
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(defaultPadding),
            ) {
                Button(
                    onClick = {
                        if (servings > 1) servings--
                        updatedIngredients = updateMeasurements(ingredients, servings, dishServings)
                    },
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape),
                ) {
                }
                Spacer(modifier = Modifier.width(defaultPadding / 1.5f))
                Text(text = "Servings " + String.format("%.0f", servings))
                Spacer(modifier = Modifier.width(defaultPadding / 1.5f))
                Button(
                    onClick = {
                        servings++
                        updatedIngredients = updateMeasurements(ingredients, servings, dishServings)
                    },
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                ) {}
            }
            Column {
                updatedIngredients.forEach { (ingredient, measurement) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(defaultPadding)
                    ) {
                        Text(
                            text = ingredient.name,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f),
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Normal,
                            //fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                        )
                        val amount = if (measurement.amount % 1 == 0f) String.format(
                            "%.0f",
                            measurement.amount
                        ) else String.format("%.2f", measurement.amount)
                        Text(
                            text = amount + measurement.name,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f),
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Normal,
                            //fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                        )
                    }
                }

            }
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
                Text(
                    text = "Instructions",
                    modifier = Modifier
                        .padding(defaultPadding),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Black,
                    //fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                )
                Text(
                    text = dish?.dishElaboration ?: "",
                    modifier = Modifier
                        .padding(defaultPadding),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal,
                    //fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                )
            }
        }
    }


    fun updateMeasurements(
        ingredients: Map<Ingridient, Mesurement>,
        servings: Float,
        dishServings: Float
    ): Map<Ingridient, Mesurement> {
        val updatedIngredients = mutableMapOf<Ingridient, Mesurement>()
        val servingsModifier: Float = servings / dishServings
        ingredients.forEach { (ingredient, measurement) ->
            updatedIngredients[ingredient] =
                Mesurement(measurement.name, measurement.amount * servingsModifier)
        }
        return updatedIngredients
    }