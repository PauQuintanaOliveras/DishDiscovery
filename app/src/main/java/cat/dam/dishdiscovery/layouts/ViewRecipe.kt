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
import androidx.compose.runtime.produceState
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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import cat.dam.dishdiscovery.objects.Ingridient
import cat.dam.dishdiscovery.Mesurement
import cat.dam.dishdiscovery.R
import cat.dam.dishdiscovery.objects.Dish
import com.google.firebase.firestore.FirebaseFirestore

//@Preview(showBackground = true)
@Composable
fun ViewRecipeScreen(dishId: String?, navController: NavController) {
    val boxSize = 100.dp
    val defaultPadding = 16.dp
    val dishServings = 4f
    var servings by remember { mutableFloatStateOf(4f) }
    //var servingsModifier by remember { mutableStateOf(1) }
    var ingridients = hashMapOf<Ingridient, Mesurement>()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val dishId = backStackEntry.value?.arguments?.getString("dishId") ?: ""

    val dish = produceState<Dish?>(initialValue = null) {
        value = getDishFromFirestore(dishId)
    }.value

    Log.d("BBBBBBBBBBBBBB", "ViewRecipeScreen: $dishId")


    //val dish = remember { mutableStateOf<Dish?>(null) }

    LaunchedEffect(dishId) {
        dish.value = getDishFromFirestore(dishId)
    }


    /*ingridients[Ingridient("Bread")] = Mesurement(" Slices", 2f)
    ingridients[Ingridient("Ham")] = Mesurement(" Slices", 1f)
    ingridients[Ingridient("Cheese")] = Mesurement(" Slices", 1.33333f)
    ingridients[Ingridient("tomato")] = Mesurement("", 1f)*/

    //Must be after Ingridients added to the list
    var updatedIngredients by remember { mutableStateOf(updateMesurements(ingridients,servings,dishServings)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
        ){
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
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(boxSize)
                .padding(defaultPadding)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp))
                .background(color = MaterialTheme.colorScheme.background)
            ){
                Column(modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = "RECIPE",modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = MaterialTheme.typography.bodySmall.fontWeight

                    )
                    Text(text = "Sandvitx",modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                    )
                    Row (modifier = Modifier.align(Alignment.CenterHorizontally)){
                        Text(text = "by ", modifier = Modifier.align(Alignment.CenterVertically),
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,

                            )
                        Text(text = "Author", modifier = Modifier.align(Alignment.CenterVertically),
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = MaterialTheme.typography.bodySmall.fontWeight
                        )
                    }

                }
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(boxSize / 1.5f)
            .background(color = MaterialTheme.colorScheme.background)
        )
        {
            //TODO: put icon and likes
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(boxSize * 1.5f)
            .background(color = MaterialTheme.colorScheme.background)
        ){
            Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi id consequat lectus, non ultricies nisl. Nullam pulvinar congue lacinia. Suspendisse non lacus in libero eleifend tincidunt sed ut velit. Mauris mattis sit amet libero pellentesque volutpat.",
                modifier = Modifier
                    .padding(defaultPadding)
                    .align(Alignment.Center)
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(boxSize * 1.25f)
            .background(color = MaterialTheme.colorScheme.background)
        ){
            Card (modifier = Modifier
                .padding(defaultPadding)
                .align(Alignment.Center)
                .fillMaxSize(),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.background),
                border = BorderStroke(1.dp, DarkGray)

            ){
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "1hr 20Min",
                        modifier = Modifier.padding(defaultPadding),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Black,

                        //fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                    )
                    Text(text = "Total Time",
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
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(defaultPadding)
            .height(boxSize / 2)
            .background(Transparent) //MaterialTheme.colorScheme.surface)
        ){
            Text(text = "Ingredients",
                modifier = Modifier
                    .align(Alignment.CenterStart),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Black,
            )
        }
        Row(modifier = Modifier
            .align(Alignment.Start)
            .padding(defaultPadding),) {
            Button(onClick = { if (servings>1) servings--
                updatedIngredients = updateMesurements(ingridients, servings, dishServings)
            },
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape),){
            }
            Spacer(modifier = Modifier.width(defaultPadding/1.5f))
            Text(text = "Servings " + String.format("%.0f", servings))
            Spacer(modifier = Modifier.width(defaultPadding/1.5f))
            Button(onClick = { servings++
                updatedIngredients = updateMesurements(ingridients, servings, dishServings)
            },
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)) {}
        }
        Column {
            updatedIngredients.forEach { (ingridient, mesurement) ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(defaultPadding)
                ) {
                    Text(text = ingridient.name,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(1f),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Normal,
                        //fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                    )
                    val amount = if (mesurement.amount % 1 == 0f) String.format("%.0f",mesurement.amount) else String.format("%.2f",mesurement.amount)
                    Text(text = amount + mesurement.name,
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
            Text(text ="Instructions",
                modifier = Modifier
                    .padding(defaultPadding),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Black,
                //fontWeight = MaterialTheme.typography.titleMedium.fontWeight
            )
            Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi id consequat lectus, non ultricies nisl. Nullam pulvinar congue lacinia. Suspendisse non lacus in libero eleifend tincidunt sed ut velit. Mauris mattis sit amet libero pellentesque volutpat.",
                modifier = Modifier
                    .padding(defaultPadding),
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal,
                //fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
            )
        }
    }
}

fun updateMesurements(ingridients: HashMap<Ingridient, Mesurement>, servings: Float, dishServings:Float): HashMap<Ingridient, Mesurement> {
    val updatedIngridients = hashMapOf<Ingridient, Mesurement>()
    val servingsModifier: Float = servings/dishServings
    ingridients.forEach { (ingridient, mesurement) ->
        updatedIngridients[ingridient] = Mesurement(mesurement.name, mesurement.amount*servingsModifier)
    }
    return updatedIngridients
}

suspend fun getDishFromFirestore(dishId: String): Dish? {
    val db = FirebaseFirestore.getInstance()
    val docRef = db.collection("Dish").document(dishId)
    var dish: Dish? = null

    docRef.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                dish = document.toObject(Dish::class.java)
            }
        }
    return dish
}

