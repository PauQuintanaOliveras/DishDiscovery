package cat.dam.dishdiscovery.layouts

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cat.dam.dishdiscovery.R
import kotlinx.coroutines.launch

@Composable
fun RecoverPassword() {
    var username by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

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
            contentDescription = R.string.logo.toString(),
            modifier = Modifier
                .size(200.dp)
                .rotate(rotation) // Apply rotation here
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(R.string.nom_d_usuari)) },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text(stringResource(R.string.nova_contrasenya)) },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = { Text(stringResource(R.string.repeteix_la_contrasenya)) },
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
            Text(stringResource(R.string.confirmar))
        }
    }
    SnackbarHost(hostState = snackbarHostState)
}