package cat.dam.dishdiscovery.layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cat.dam.dishdiscovery.R
import com.google.firebase.firestore.FirebaseFirestore

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
