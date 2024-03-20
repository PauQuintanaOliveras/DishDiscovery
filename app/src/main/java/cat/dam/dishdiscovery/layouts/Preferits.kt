package cat.dam.dishdiscovery.layouts

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.dam.dishdiscovery.DishCard
import cat.dam.dishdiscovery.R
import cat.dam.dishdiscovery.navbar

const val descripcioSandvitx="Tros de pa obert per la meitat o dues llesques de pa amb embotit, formatge o un altre menjar a dins"
const val decripcioSopar="Plat típic de la cuina japonesa que consisteix en una sopa feta amb brou de carn o verdures i salsa de soja al que s'afegeixen uns fideus llargs"
const val descripcioPasta="Pasta alimentària de farina en forma de fil llarg, més gruixut que el fideu."
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Preferits(navController: NavController, isPreferits: Boolean)
{
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
                    IconButton(onClick = { navController.navigate("Settings") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings Icon")
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.primaryContainer // color de fondo del TopAppBar
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
                LazyColumn(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(30.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        DishCard().BasicCardPreview(
                            "Sandvitx",
                            descripcioSandvitx,
                            R.drawable.sandwich,
                            navController,
                            isPreferits
                        )

                    }
                    item {
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                    item {
                        DishCard().BasicCardPreview(
                            "Sopar",
                            decripcioSopar,
                            R.drawable.sopa,
                            navController,
                            isPreferits
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                    item {
                        DishCard().BasicCardPreview(
                            "Pasta",
                            descripcioPasta,
                            R.drawable.pasta,
                            navController,
                            isPreferits
                        )
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
           /* Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                content = {
                    IconButton(onClick = { navController.navigate("main_page") }) {
                        val painter = painterResource(id = R.drawable.preferits)
                        Icon(painter = painter, contentDescription = "First Icon", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    IconButton(onClick = { navController.navigate("main_page") }) {
                        val painter = painterResource(id = R.drawable.descobrir)
                        Icon(painter = painter, contentDescription = "Second Icon", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    IconButton(onClick = { navController.navigate("map") }) {
                        val painter = painterResource(id = R.drawable.botiga)
                        Icon(painter = painter, contentDescription = "Third Icon", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            )
        */}
    )
}
@Preview
@Composable
fun PreviewScaffoldWithTopBarAndButtonBar() {
    Preferits(rememberNavController(), isPreferits = true)
}