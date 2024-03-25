package cat.dam.dishdiscovery.layouts

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.dam.dishdiscovery.DishCard
import cat.dam.dishdiscovery.Mesurement
import cat.dam.dishdiscovery.R
import cat.dam.dishdiscovery.navbar
import cat.dam.dishdiscovery.objects.Diet
import cat.dam.dishdiscovery.objects.Dish
import cat.dam.dishdiscovery.objects.DishHeader
import cat.dam.dishdiscovery.objects.Ingridient
import cat.dam.dishdiscovery.objects.MealType
import cat.dam.dishdiscovery.objects.Tag
import cat.dam.dishdiscovery.objects.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

val client = Firebase.auth.currentUser?.uid

val descripcioSandvitx="Tros de pa obert per la meitat o dues llesques de pa amb embotit, formatge o un altre menjar a dins"
val descripcioSopar="Plat típic de la cuina japonesa que consisteix en una sopa feta amb brou de carn o verdures i salsa de soja al que s'afegeixen uns fideus llargs"
val descripcioPasta="Pasta alimentària de farina en forma de fil llarg, més gruixut que el fideu."

val diets = listOf(
    Diet("Vegana"),
    Diet("Vegatariana"),
    Diet("Carnivora")
)
val ingridient = Ingridient("Ingridient1")
val mesurement = Mesurement("unit", 1f)
val mapingmes = mapOf<Ingridient,Mesurement>(ingridient to mesurement)
val dish = Dish(mutableStateOf("very hard elabotarion"), "lmao", 3)
val author = User(client,"Username",false, listOf("dish1","dish2"),"mealplaner",false, listOf("dish1","dish2"))
val mealType = MealType("Lunch")
val tags = listOf(
    Tag("yummy"),
    Tag("veryEasy")
)
val dishHeaders = listOf(
    DishHeader(diets, dish, author, descripcioSandvitx, "Dish1","imageName", mealType,false,true,
        tags),
    DishHeader(diets, dish, author, descripcioSopar, "Dish2","imageName", mealType,false,true,
        tags),
    DishHeader(
        diets, dish, author, descripcioPasta, "Dish3", "imageName", mealType, false, true,
        tags
    ),
    DishHeader(diets, dish, author, descripcioSandvitx, "Dish4","imageName", mealType,false,true,
        tags),
    DishHeader(diets, dish, author, descripcioSandvitx, "Dish5","imageName", mealType,false,true,
        tags),
    DishHeader(diets, dish, author, descripcioSandvitx, "Dish6","imageName", mealType,false,true,
        tags),
    DishHeader(diets, dish, author, descripcioSandvitx, "Dish7","imageName", mealType,false,true,
        tags),
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Preferits(navController: NavController, isPreferits: Boolean) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedFilters by remember { mutableStateOf(listOf<String>()) }
    val db = FirebaseFirestore.getInstance()

    db.collection("DishHeader").get()
        .addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                Log.d("Firebase", "DocumentSnapshot data: ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.d("Firebase", "get failed with ", exception)
        }

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)) {
                Column {
                    Text("Filtros seleccionados", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(8.dp))
                    ChipGroup(items = selectedFilters, selectedItems = selectedFilters, onChipClick = { filter ->
                        selectedFilters = selectedFilters.filter { it != filter }
                    }, isFilterSelected = true)
                    Text("Dietas", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(8.dp))
                    ChipGroup(items = listOf("Vegana", "Vegetariana"), selectedItems = selectedFilters, onChipClick = { filter ->
                        if (selectedFilters.contains(filter)) {
                            selectedFilters = selectedFilters.filter { it != filter }
                        } else {
                            selectedFilters = selectedFilters + filter
                        }
                    })

                    Text("Tipos de Comida", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(8.dp))
                    ChipGroup(items = listOf("Desayuno", "Almuerzo"), selectedItems = selectedFilters, onChipClick = { filter ->
                        if (selectedFilters.contains(filter)) {
                            selectedFilters = selectedFilters.filter { it != filter }
                        } else {
                            selectedFilters = selectedFilters + filter
                        }
                    })

                    ChipGroup(items = listOf("Tomate", "Bistec"), selectedItems = selectedFilters, onChipClick = { filter ->
                        if (selectedFilters.contains(filter)) {
                            selectedFilters = selectedFilters.filter { it != filter }
                        } else {
                            selectedFilters = selectedFilters + filter
                        }
                    })
                    Text("Ingredientes", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(8.dp))
                    ChipGroup(items = listOf("Pollo", "Pescado"), selectedItems = selectedFilters, onChipClick = { filter ->
                        if (selectedFilters.contains(filter)) {
                            selectedFilters = selectedFilters.filter { it != filter }
                        } else {
                            selectedFilters = selectedFilters + filter
                        }
                    })

                    Button(onClick = { /* Aquí va la lógica para aplicar los filtros */ }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Text("Aplicar")
                    }
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .clip(RoundedCornerShape(60.dp)),
                        title = {
                            var searchText by remember { mutableStateOf("") }
                            TextField(
                                value = searchText,
                                onValueChange = { searchText = it },
                                placeholder = { Text(if (isPreferits)"Cercar Receptes Guardades" else "Cercar Receptes") },
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = MaterialTheme.colorScheme.onSurface,
                                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                            )
                        },

                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu Icon")
                            }
                        },
                        actions = {
                            IconButton(onClick = { navController.navigate("Settings") }) {
                                Icon(Icons.Filled.Settings, contentDescription = "Settings Icon")
                            }
                        },
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer
                    )
                },
                content = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.fonsblurred5),
                            contentDescription = "Background",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 80.dp)) {
                            LazyColumn(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(30.dp),

                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                for (header in dishHeaders) {
                                    item {
                                        DishCard().BasicCardPreview(
                                            header.dishName,
                                            header.dishDescription,
                                            R.drawable.sandwich,
                                            navController,
                                            isPreferits
                                        )
                                        Spacer(modifier = Modifier.size(25.dp))
                                    }
                                }
                            }
                        }
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { navController.navigate("create_recipe")}) {
                        Icon(Icons.Filled.Add, contentDescription = "Add")
                    }
                },
                bottomBar = {
                    navbar(navController = navController, current = 2)
                }
            )
        }
    )
}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String, onChipClick: (String) -> Unit, isSelected: Boolean, isFilterSelected: Boolean = false) {
    Box(
        modifier = modifier
            .clickable { onChipClick(text) }
            .background(if (isSelected) Color.Gray else MaterialTheme.colorScheme.primaryContainer)
            .border(1.dp, Color.Gray, RoundedCornerShape(50))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = text, textDecoration = if (isSelected && !isFilterSelected) TextDecoration.LineThrough else TextDecoration.None)
    }
}

@Composable
fun ChipGroup(items: List<String>, selectedItems: List<String>, onChipClick: (String) -> Unit = {}, isFilterSelected: Boolean = false) {
    LazyRow {
        items(items) { item ->
            Chip(modifier = Modifier.padding(8.dp), text = item, onChipClick = onChipClick, isSelected = item in selectedItems, isFilterSelected = isFilterSelected)
        }
    }
}


@Preview
@Composable
fun PreviewScaffoldWithTopBarAndButtonBar() {
    Preferits(rememberNavController(), isPreferits = true)
}