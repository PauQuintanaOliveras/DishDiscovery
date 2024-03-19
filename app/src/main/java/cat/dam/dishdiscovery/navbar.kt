package cat.dam.dishdiscovery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController

@Composable
fun navbar(navController: NavController, current: Int){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer),
        content = {
            IconButton(onClick = { navController.navigate("main_page") },
                modifier = Modifier.background(if(current == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)) {
                val painter = painterResource(id = R.drawable.preferits)

                Icon(painter = painter, contentDescription = "First Icon", tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
            IconButton(onClick = { navController.navigate("main_page") },
                modifier = Modifier.background(if(current == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)) {
                val painter = painterResource(id = R.drawable.descobrir)
                Icon(painter = painter, contentDescription = "Second Icon", tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
            IconButton(onClick = { navController.navigate("map") },
                modifier = Modifier.background(if(current == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)) {
                val painter = painterResource(id = R.drawable.botiga)
                Icon(painter = painter, contentDescription = "Third Icon", tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    )
}