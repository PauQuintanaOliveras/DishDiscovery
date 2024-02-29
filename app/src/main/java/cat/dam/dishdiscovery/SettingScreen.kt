package cat.dam.dishdiscovery

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
@Preview(showBackground = true)
@Composable
fun SettingsScreen(
//default values for the preview
    userName: String = "John Doe",
    password: String = "********",
    onRecipesClick: () -> Unit = {},
    onDarkModeToggle: (Boolean) -> Unit = {},
    onReturnClick: () -> Unit = {},
    //userName: String,
    //password: String,
    //onRecipesClick: () -> Unit,
    //onDarkModeToggle: (Boolean) -> Unit,
    //onReturnClick: () -> Unit
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