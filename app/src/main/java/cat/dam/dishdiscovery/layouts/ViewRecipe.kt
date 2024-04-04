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
import cat.dam.dishdiscovery.R
import cat.dam.dishdiscovery.objects.Dish
import cat.dam.dishdiscovery.objects.Ingridient
import cat.dam.dishdiscovery.objects.Mesurement
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun ViewRecipeScreen(dishId: String?) {
    val boxSize = 100.dp
    val defaultPadding = 16.dp
    val db = FirebaseFirestore.getInstance()
    var dish by remember { mutableStateOf<Dish?>(null) }
    var dishServings = dish?.dishServings?.toFloat() ?: 1f
    var servings by remember { mutableFloatStateOf(dishServings) }
    var updatedIngredients by remember {
        mutableStateOf(
            updateMeasurements(
                dish?.ingridientsQty ?: mapOf(),
                servings,
                dishServings
            )
        )
    }

    LaunchedEffect(dishId) {
        dishId?.let {
            val dishRef = db.collection("Dish").document(it)
            dishRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    dish = document.toObject(Dish::class.java)
                    var ingridientsQtyRef = dishRef.collection("IngridientsQty")
                    ingridientsQtyRef.get().addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            for (document in documents) {
                                Log.d("ViewRecipeScreen", "DocumentSnapshot data: ${document.data}")
                                //var test = document.getDouble("Qty")?.toFloat() ?: 0f
                                //Log.d("test", "test: $test")
                                if (document != null) {
                                    db.collection("Ingridient").document(document.id).get()
                                        .addOnSuccessListener { ingDoc ->
                                            val tempIngridient: Ingridient = Ingridient(
                                                ingDoc.getString("IngridientName") ?: "Empty"
                                            )
                                            var tmpMesurementName: String = "empty"
                                            val measurementRef =
                                                document.getDocumentReference("Mesurement")
                                            measurementRef?.get()
                                                ?.addOnSuccessListener { measurementDocument ->
                                                    Log.d(
                                                        "AAAAAAAAAAAAAAAAAAAAAAAAAAA",
                                                        "DocumentSnapshot data: ${measurementDocument.data}"
                                                    )
                                                    tmpMesurementName =
                                                        measurementDocument.getString("MesurementName")
                                                            ?: "empty"
                                                    Log.d(
                                                        "AAAAAAAAAAAAAAAAAAAAAAAAAAA",
                                                        "MesurementName: $tmpMesurementName"
                                                    )
                                                    // Now you can use the `measurement` object

                                                    val tempMesurement: Mesurement = Mesurement(
                                                        tmpMesurementName,
                                                        document.getDouble("Qty")?.toFloat() ?: 0f
                                                    )
                                                    dish?.ingridientsQty?.set(
                                                        tempIngridient,
                                                        tempMesurement
                                                    )
                                                    Log.d("tempIng", "Ingridient: $tempIngridient")
                                                    Log.d("tempMes", "Mesurement: $tempMesurement")
                                                    dishServings = dish?.dishServings ?: 1f
                                                    servings = dish?.dishServings ?: 1f
                                                    updatedIngredients =
                                                        updateMeasurements(
                                                            dish?.ingridientsQty ?: mapOf(),
                                                            servings,
                                                            dishServings
                                                        )
                                                }
                                        }
//                                   //ingridientsQty.ingridient = document.id
                                    //ingridientsQty.mesurement = document.getDocumentReference("Mesurement")?.id ?: ""
                                    //ingridientsQty.qty = document.getDouble("Qty")?.toFloat() ?: 0f
                                }
                            }
                        } else {
                            Log.d("ViewRecipeScreen", "No document found with DishName: $it")
                        }
                    }
                } else {
                    Log.d("ViewRecipeScreen", "No document found with DishName: $it")
                }
                Log.d("doc data", "DocumentSnapshot data: ${document.data}")
                Log.d("dish data", "Dish data: ${dish}")
                Log.d("ingridientsQty data", "IngridientsQty data: ${dish?.ingridientsQty}")
            }


            //var ingridientsQty : IngridientQty? = null

        }
    }


    //Must be after Ingridients added to the list
//    var updatedIngredients by remember {
//        mutableStateOf(
//            updateMeasurements(
//                dish?.ingridientsQty ?: mapOf(),
//                servings,
//                dishServings
//            )
//        )
//    }

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
                    updatedIngredients =
                        updateMeasurements(dish?.ingridientsQty ?: mapOf(), servings, dishServings)
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
                    updatedIngredients =
                        updateMeasurements(dish?.ingridientsQty ?: mapOf(), servings, dishServings)
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
                    val amount =
                        if (measurement.quantity == 0f) "" else if (measurement.quantity % 1 == 0f) String.format(
                            "%.0f",
                            measurement.quantity
                        ) else String.format("%.2f", measurement.quantity)
                    Text(
                        text = amount + " " + measurement.mesurementName,
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
            //val elaboration = dish?.dishElaboration?.split("\n")
            //elaboration?.forEach {
            //    Log.d("elaboration", it)
            val elaboration = dish?.dishElaboration?.replace("\\n", "\n\n") ?: ""
            Text(
                text = elaboration,
                modifier = Modifier
                    .padding(defaultPadding),
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal,
                //fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
            )
            //}
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
            Mesurement(measurement.mesurementName, measurement.quantity * servingsModifier)
    }
    return updatedIngredients
}