package cat.dam.dishdiscovery
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CrearRecepte {

    @Preview
    @Composable
    fun DisplayText() {
        val textState = remember { mutableStateOf("") }
        val productState = remember { mutableStateOf("") }
        val checkboxState1 = remember { mutableStateOf(false) } // Add this line
        val checkboxState2 = remember { mutableStateOf(false) }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Crear Recepte",
                fontSize = 24.sp,
                onTextLayout = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Titol Recepte",
                fontSize = 10.sp,
                onTextLayout = {}
            )
            Spacer(modifier = Modifier.height(11.dp))
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "",
                        fontSize = 10.sp,
                        onTextLayout = {}
                    )

                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Afegir Ingredients",
                fontSize = 10.sp,
                onTextLayout = {}
            )
            Spacer(modifier = Modifier.height(11.dp))
            TextField( // Add this TextField
                value = productState.value,
                onValueChange = { productState.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "",
                        fontSize = 10.sp,
                        onTextLayout = {}
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Instruccions dels Ingredients (opcional)",
                fontSize = 10.sp,
                onTextLayout = {}
            )
            Spacer(modifier = Modifier.height(11.dp))
            TextField( // Add this TextField
                value = productState.value,
                onValueChange = { productState.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "",
                        fontSize = 10.sp,
                        onTextLayout = {}
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Notes de l'autor (opcional)",
                fontSize = 10.sp,
                onTextLayout = {}
            )
            Spacer(modifier = Modifier.height(11.dp))
            TextField( // Add this TextField
                value = productState.value,
                onValueChange = { productState.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "",
                        fontSize = 10.sp,
                        onTextLayout = {}
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checkboxState1.value,
                    onCheckedChange = { checkboxState1.value = it }
                )
                Text(

                    text = "Private",
                    fontSize = 10.sp,
                    onTextLayout = {}
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checkboxState2.value,
                    onCheckedChange = { checkboxState2.value = it }
                )
                Text(

                    text = "Public",
                    fontSize = 10.sp,
                    onTextLayout = {}
                )
            }
            Button(
                onClick = { /* Handle button click */ },
            modifier=Modifier
                .align(Alignment.CenterHorizontally)
                .size(200.dp,50.dp)

            ) {
                Text( text = "Fet",
                    fontSize = 10.sp,
                    onTextLayout = {})
            }
        }
    }

}