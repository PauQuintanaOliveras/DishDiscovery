package cat.dam.dishdiscovery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.dam.dishdiscovery.ui.theme.DishDiscoveryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingsScreen(
                userName = "AlbertTuvi1",
                password = "password",
                onRecipesClick = { /* Navegar a las recetas guardadas */ },
                onDarkModeToggle = { isDarkMode -> /* Cambiar el tema de la aplicación */ },
                onReturnClick = { /* Volver al menú */ }
            )
        }
        }
    }

@Composable
fun SettingsScreen(
    userName: String,
    password: String,
    onRecipesClick: () -> Unit,
    onDarkModeToggle: (Boolean) -> Unit,
    onReturnClick: () -> Unit
) {
    val darkModeState = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(32.dp)) // Increased spacer height
            Text(text = "Nombre de usuario: $userName")
            Spacer(modifier = Modifier.height(32.dp)) // Increased spacer height
            Text(text = "Contraseña: ${"*".repeat(password.length)}")
            Spacer(modifier = Modifier.height(32.dp)) // Increased spacer height
            Button(onClick = onRecipesClick) {
                Text(text = "Les Meves Receptes")
            }
            Spacer(modifier = Modifier.height(32.dp)) // Increased spacer height
            Switch(
                checked = darkModeState.value,
                onCheckedChange = { isChecked ->
                    darkModeState.value = isChecked
                    onDarkModeToggle(isChecked)
                }
            )
            Spacer(modifier = Modifier.height(32.dp)) // Increased spacer height
            Button(onClick = onReturnClick) {
                Text(text = "Retornar")
            }
            Spacer(modifier = Modifier.weight(1f)) // This will push the content to the top and fill the remaining space
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen(
        userName = "AlbertTuvi1",
        password = "password",
        onRecipesClick = { /* Navegar a las recetas guardadas */ },
        onDarkModeToggle = { isDarkMode -> /* Canviar el tema de la apliacio */ },
        onReturnClick = { /* Tornar al menú */ }
    )
}