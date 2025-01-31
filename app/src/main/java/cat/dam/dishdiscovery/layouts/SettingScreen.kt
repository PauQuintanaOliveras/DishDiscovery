package cat.dam.dishdiscovery.layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cat.dam.dishdiscovery.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun SettingsScreen(
    navController: NavController,
    userName: String = getUser(),
    password: String = "********",
    onRecipesClick: () -> Unit = {},
) {
    val darkModeState = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = R.string.logo.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "${R.string.nom_d_usuari}: $userName")
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "${R.string.contrasenya}: ${"*".repeat(password.length)}")
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onRecipesClick) {
                Text(text = "Les Meves Receptes")
            }
            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navController.navigate("main_page") }) {
                Text(text = stringResource(R.string.retornar))
            }
            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { delteUser() }) {
                Text(text = stringResource(R.string.eliminar_compte))
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

fun getUser():  String {
    val client = Firebase.auth.currentUser
    return client?.email.toString()
}

fun delteUser() {
    val client = Firebase.auth.currentUser
    client?.delete()
}