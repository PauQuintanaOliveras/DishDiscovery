package cat.dam.dishdiscovery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.dam.dishdiscovery.layouts.LogInScreen
import cat.dam.dishdiscovery.layouts.Preferits
import cat.dam.dishdiscovery.layouts.RecoverPassword
import cat.dam.dishdiscovery.layouts.SignIn
import cat.dam.dishdiscovery.layouts.ViewRecipeScreen
import cat.dam.dishdiscovery.ui.theme.DishDiscoveryTheme
import cat.dam.dishdiscovery.layouts.CreateRecipe


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
                        composable("login_screen") { LogInScreen(navController) }
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







