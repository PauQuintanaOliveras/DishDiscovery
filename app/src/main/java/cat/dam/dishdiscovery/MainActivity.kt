package cat.dam.dishdiscovery

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Switch
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.dam.dishdiscovery.ui.theme.DishDiscoveryTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val startDestination =
                if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) "login_screen" else "main_page" // If the user is not logged in, the start destination is the login screen
            DishDiscoveryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(navController, startDestination = startDestination) {
                        composable("login_screen") { LoginScreen(navController) }
                        composable("sign_in_screen") { SignIn(navController) }
                        composable("recover_password_screen") { RecoverPassword() }
                        composable("main_page") { ScaffoldWithTopBarAndButtonBar() }
                    }
                }
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
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
        val snackbarHostState = remember { SnackbarHostState() }
        val loginViewModel: LoginScreenViewModel = viewModel()
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts
                .StartActivityForResult()

        ) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                loginViewModel.logInWithGoogleCredentials(credential) {
                    navController.navigate("main_page") {
                        // Pop up to the login screen, removing it from the back stack
                        popUpTo("login_screen") {
                            inclusive = true
                        }
                    }

                }
            } catch (e: ApiException) {
                Log.w("GoogleSignIn", "signInResult:failed code=" + e.statusCode)
            }
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
                label = { Text("Correu Electronic") },
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

            Button(onClick = {
                loginViewModel.logInWithEmailAndPassword(username.value.text, password.value.text) {
                    navController.navigate("main_page")
                }
            }) {
                Text("Accedir")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val options = GoogleSignInOptions.Builder(
                    GoogleSignInOptions.DEFAULT_SIGN_IN
                )
                    .requestIdToken("819336651564-ataubgncel95p2i9or6j6ho51oluo0h6.apps.googleusercontent.com")
                    .requestEmail()
                    .build()
                val signInClient = GoogleSignIn.getClient(context, options)
                launcher.launch(signInClient.signInIntent)

            }) {
                Image(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.googlelogo), contentDescription = ""
                )
                Text("Accedir amb Google")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Registrar-se",
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { navController.navigate("sign_in_screen") }

            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Has oblidat la contrasenya?",
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { navController.navigate("recover_password_screen") }
            )
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
                if (!isValidInput(username) || !isValidInput(newPassword) || !isValidInput(
                        repeatPassword
                    )
                ) {
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
    fun SignIn(navController: NavController) {
        val db = FirebaseFirestore.getInstance()
        val username = remember { mutableStateOf(TextFieldValue()) }
        val email = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        val signInScreenViewModel: SignInScreenViewModel = viewModel()

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
                signInScreenViewModel.signIn(email.value.text, password.value.text) {
                    signInScreenViewModel.createUser()
                    navController.navigate("main_page")
                }
                /*if (!isValidInput(username.value.text) || !isValidInput(password.value.text)) {
                    // Muestra un mensaje de error
                    scope.launch {
                        snackbarHostState.showSnackbar("L'usuari només pot accedir si afegeix a-z, A-Z, 0-9 o _")
                    }
                } else {
                    // Continúa con la creación del usuario
                    val user = hashMapOf(
                        "Username" to username.value.text,
                        "Email" to email.value.text,
                        "Password" to password.value.text
                    )*/


                /*db.collection("User")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        scope.launch {
                            snackbarHostState.showSnackbar("Usuario creado exitosamentet")
                            navController.navigate("main_page")
                        }
                    }
                    .addOnFailureListener { e ->
                        // Error al crear el usuario
                        scope.launch {
                            snackbarHostState.showSnackbar("Error al crear el usuario")
                        }
                    }
            }*/
            }) {
                Text(text = "Registrar-se")
            }
        }
        SnackbarHost(hostState = snackbarHostState)
    }


    @Composable
    fun PreviewLoginScreen() {
        val navController = rememberNavController()
        DishDiscoveryTheme {
            LoginScreen(navController)
        }
    }

    fun CheckUserExists() {
        val db = FirebaseFirestore.getInstance()
        db.collection("User")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.data["Username"] == "username") {
                    }
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }
}
