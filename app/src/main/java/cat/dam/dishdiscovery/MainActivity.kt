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
import androidx.compose.material3.Surface
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
import cat.dam.dishdiscovery.layouts.LogInScreen
import cat.dam.dishdiscovery.layouts.Preferits
import cat.dam.dishdiscovery.layouts.RecoverPassword
import cat.dam.dishdiscovery.layouts.SignIn
import cat.dam.dishdiscovery.layouts.ViewRecipeScreen
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
                        composable("main_page") { Preferits(navController) }
                        composable("view_recipe_screen") { ViewRecipeScreen() }
                        composable("create_recipe") { CreateRecipe() }
                    }
                }
            }
        }
    }
}







