package cat.dam.dishdiscovery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.dam.dishdiscovery.ui.theme.DishDiscoveryTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            DishDiscoveryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(navController, startDestination = "login_screen") {
                        composable("login_screen") { LoginScreen(navController) }
                        composable("sign_in_screen") { SignIn() }
                        composable("recover_password_screen") { RecoverPassword() }
                    }
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
@Composable
fun LoginScreen(navController: NavController) {
    val username = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val logo = painterResource(id = R.drawable.logo)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun isValidInput(input: String): Boolean {
        val pattern = Regex("^[a-zA-Z0-9_]*$")
        return pattern.matches(input)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = logo,
            contentDescription = "Logo",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Usuari") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Contrasenya") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Has oblidat la contrasenya?",
            modifier = Modifier.clickable { navController.navigate("recover_password_screen") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (!isValidInput(username.value.text) || !isValidInput(password.value.text)) {
                // Muestra un mensaje de error
                scope.launch {
                    snackbarHostState.showSnackbar("L'usuari només pot accedir si afegeix a-z, A-Z, 0-9 o _")
                }
            } else {
                // Continúa amb l' inici de sessio
            }
        }) {
            Text("Accedir")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Registrar-se",
            modifier = Modifier.clickable { navController.navigate("sign_in_screen") })
    }
    SnackbarHost(hostState = snackbarHostState)
}


@Composable
fun RecoverPassword() {
    var username by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun isValidInput(input: String): Boolean {
        val pattern = Regex("^[a-zA-Z0-9_]*$")
        return pattern.matches(input)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nom d'Usuari") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("Nova Contrasenya") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = { Text("Repeteix la Contrasenya") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (!isValidInput(username) || !isValidInput(newPassword) || !isValidInput(repeatPassword)) {
                //Posa un missatge d'error
                scope.launch {
                    snackbarHostState.showSnackbar("L'usuari només pot accedir si afegeix a-z, A-Z, 0-9 o _")
                }
            } else {
                // Aquí va la lógica de confirmació
            }
        }) {
            Text("Confirmar")
        }
    }
    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun SignIn() {
    val username = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun isValidInput(input: String): Boolean {
        val pattern = Regex("^[a-zA-Z0-9_]*$")
        return pattern.matches(input)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("nom Usuari") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Correu Electrònic") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Contrasenya") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (!isValidInput(username.value.text) || !isValidInput(password.value.text)) {
                // Muestra un mensaje de error
                scope.launch {
                    snackbarHostState.showSnackbar("L'usuari només pot accedir si afegeix a-z, A-Z, 0-9 o _")
                }
            } else {
                // Continúa amb l' inici de sessio
            }
        }) {
            Text("Registrar-se")
        }
    }
    SnackbarHost(hostState = snackbarHostState)
}
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    DishDiscoveryTheme {
        LoginScreen(navController)
    }
}





