package cat.dam.dishdiscovery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


class DishCard {
    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun BasicCardPreview(
        tag: String = "none",
        dishDescription: String = "none",
        dishImage: Int = R.drawable.ic_launcher_background,

    ) {
        ElevatedCard(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            //placeholder for card color
            colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp),
            // Experimental Shit remove if Shit Breaks :3
            onClick = { /*TODO*/ },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    //placeholder for dish image
                    CardImagePreview(dishImage)
                    //placeholder for favorite icon
                    Icon(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(6.dp, 6.dp, 0.dp, 0.dp),
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "Favorite",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Button(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 1.dp, 6.dp, 0.dp),
                        onClick = { /*TODO*/ },
                        //placeholder for tag colors
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
                            color = Color(0xFF201A17),
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
    fun CardImagePreview(dishImage: Int) {
        return Image(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clip(RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp)),
            //placeholder for dish image
            painter = painterResource(id = dishImage),
            contentDescription = "Dish Image",

        )

    }
}