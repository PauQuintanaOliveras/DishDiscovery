package cat.dam.dishdiscovery.layouts

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.dam.dishdiscovery.DishCard
import cat.dam.dishdiscovery.R

const val descripcioSandvitx="Tros de pa obert per la meitat o dues llesques de pa amb embotit, formatge o un altre menjar a dins"
const val decripcioSopar="Plat típic de la cuina japonesa que consisteix en una sopa feta amb brou de carn o verdures i salsa de soja al que s'afegeixen uns fideus llargs"
const val descripcioPasta="Pasta alimentària de farina en forma de fil llarg, més gruixut que el fideu."
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Preferits(navController: NavController)
{

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(top = 50.dp) // Mueve el TopAppBar hacia abajo
                    .clip(RoundedCornerShape(60.dp)), // Hace los bordes redondos
                title = {
                    Text(
                        text = "Cercar Receptes Guardades",
                        onTextLayout = {}
                    ) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation icon press */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Navigation Icon")
                    }
                }
            )
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.fondo),
                    contentDescription = "Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                LazyColumn(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(30.dp)
                        .fillMaxWidth()
                ) {
                    item {
                        DishCard().BasicCardPreview("Sandvitx", descripcioSandvitx,
                            R.drawable.sandwich
                        )

                    }
                    item {
                        Spacer(modifier = Modifier.height(40.dp)) // Agrega un espacio entre las tarjetas
                    }
                    item {
                        DishCard().BasicCardPreview(
                            "Sopar",
                            decripcioSopar,
                            R.drawable.sopa,

                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(40.dp)) // Agrega un espacio entre las tarjetas
                    }
                    item {
                        DishCard().BasicCardPreview(
                            "Pasta",
                            descripcioPasta,
                            R.drawable.pasta,

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



            

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /* Handle first icon press */ }) {
                    val painter = painterResource(id = R.drawable.preferits)
                    Icon(painter = painter, contentDescription = "First Icon")
                }
                IconButton(onClick = { /* Handle first icon press */ }) {
                    val painter = painterResource(id = R.drawable.descobrir)
                    Icon(painter = painter, contentDescription = "Second Icon")
                }
                IconButton(onClick = { /* Handle first icon press */ }) {
                    val painter = painterResource(id = R.drawable.botiga)
                    Icon(painter = painter, contentDescription = "Third Icon")
                }
            }
        }
    )
}
@Preview
@Composable
fun PreviewScaffoldWithTopBarAndButtonBar() {
    Preferits(rememberNavController())

}