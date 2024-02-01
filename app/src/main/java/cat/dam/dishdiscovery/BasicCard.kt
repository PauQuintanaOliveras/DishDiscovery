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
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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


class DishCard {
    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun BasicCardPreview() {
        val tag = "test!"
        ElevatedCard(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            //placeholder for card color
            colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFFDF1EB)),
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
                    CardImagePreview()

                    //placeholder for favorite icon
                    Icon(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(6.dp, 6.dp, 0.dp, 0.dp),
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "Favorite",
                        tint = Color(0xFFFFB77F),
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
                            containerColor = Color(0xFFFFB77F),
                            contentColor = Color(0xFF4E2600),
                        ),
                        shape = RoundedCornerShape(30.dp, 20.dp, 0.dp, 30.dp),

                        ) {
                        Text(text = tag)
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxSize(),
                        text = "Lorem ipsum ibulum neque metustis metus. Integer eleifend, ipsum ulvinar condimentum, ipsum. Cras auctor nibh nisl, molestie.",
                        style = TextStyle(
                            fontSize = 12.sp,
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
    fun CardImagePreview() {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFDF1EB))
                .clip(RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp)),
            //placeholder for dish image
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Dish Image",
        )
    }
}