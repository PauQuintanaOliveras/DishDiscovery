package cat.dam.dishdiscovery

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

class DishCard {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BasicCardPreview(
        id: String,
        dishName: String = "none",
        tag: String = "none",
        dishDescription: String = "none",
        dishImage: Uri? = Uri.EMPTY,
        navController: NavController,
        isPreferits: Boolean
    ) {
        Log.d("DishImageFromCard", "BasicCardPreview: $dishImage")
        ElevatedCard(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp),
            onClick = { navController.navigate("view_recipe_screen/${id}") },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    CardImagePreview(navController, dishImage)
                    Icon(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(6.dp, 6.dp, 0.dp, 0.dp),
                        painter = if (isPreferits) painterResource(id = R.drawable.outline_favorite_24) else painterResource(
                            id = R.drawable.outline_favorite_border_24),
                        contentDescription = "Favorite",
                        tint = MaterialTheme.colorScheme.primary,

                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Button(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 1.dp, 6.dp, 0.dp),
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        shape = RoundedCornerShape(30.dp, 20.dp, 0.dp, 30.dp),

                        ) {
                        Text(text = tag)
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxSize(),
                        text = dishDescription,
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            letterSpacing = 0.5.sp,
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 7,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    @Composable
    fun CardImagePreview(navController: NavController, dishImage: Uri?) {
        Log.d("CurrentCardImage", "CardImagePreview: $dishImage")
        val painter = rememberImagePainter(data = dishImage)

        Image(
            painter = painter,
            contentDescription = "DishImage",
            contentScale= ContentScale.Crop,
            modifier = Modifier.fillMaxSize()

        )
    }
}