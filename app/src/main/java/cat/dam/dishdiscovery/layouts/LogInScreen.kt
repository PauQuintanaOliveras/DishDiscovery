package cat.dam.dishdiscovery.layouts

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cat.dam.dishdiscovery.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider


@Composable
fun LogInScreen(navController: NavController) {
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

    // Add rotation state for the logo
    val rotationState = remember { mutableStateOf(0f) }
    val rotation by animateFloatAsState(
        targetValue = rotationState.value,
        animationSpec = tween(
            durationMillis = 2000,
            easing = LinearEasing
        )
    )

    LaunchedEffect(key1 = true) {
        rotationState.value = 360f
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
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .rotate(rotation)
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