package cat.dam.dishdiscovery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.dam.dishdiscovery.layouts.CreateRecipe
import cat.dam.dishdiscovery.layouts.LogInScreen
import cat.dam.dishdiscovery.layouts.MapScreen
import cat.dam.dishdiscovery.layouts.Preferits
import cat.dam.dishdiscovery.layouts.RecoverPassword
import cat.dam.dishdiscovery.layouts.SettingsScreen
import cat.dam.dishdiscovery.layouts.SignIn
import cat.dam.dishdiscovery.layouts.ViewRecipeScreen
import cat.dam.dishdiscovery.ui.theme.DishDiscoveryTheme
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        Firebase.initialize(context = this)
        Firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance(),
        )
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var startDestination =
                if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) "login_screen" else "main_page" // If the user is not logged in, the start destination is the login screen
            DishDiscoveryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(navController, startDestination = startDestination) {
                        composable("login_screen") { LogInScreen(navController) }
                        composable("sign_in_screen") { SignIn(navController) }
                        composable("recover_password_screen") { RecoverPassword() }
                        composable("main_page") { Preferits(navController,false) }
                        composable("preferits") { Preferits(navController,true) }
                        //composable("view_recipe_screen") { ViewRecipeScreen() }
                        composable("view_recipe_screen/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")
                            ViewRecipeScreen(id)
                        }
                        composable("create_recipe") { CreateRecipe() }
                        composable("map") { MapScreen(navController) }
                        composable("temp") { searchbar() }
                        composable("Settings") { SettingsScreen(navController) }
                    }
                }
            }
        }
    }
}