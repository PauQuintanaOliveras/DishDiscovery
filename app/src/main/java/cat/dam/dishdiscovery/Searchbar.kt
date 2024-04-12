package cat.dam.dishdiscovery

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchbar(
    //searchQuery: String,
    //searchResult: List<Ingridient>,
    //onSearchQueryChange: (String) -> Unit
    items : List<String> = listOf("empty")
): String {
    var text by remember {mutableStateOf("") }
    var active by remember {mutableStateOf(false) }
    /*items = remember { mutableStateListOf(
        "Ingridient 1",
        "Ingridient 2",
    )}*/

    val itemList by remember { mutableStateOf(items) }

    SearchBar(
            modifier = Modifier
                .padding()
                .fillMaxWidth()
                .heightIn(max = 600.dp),
            query = text,
            onQueryChange = { text = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text("Cerca un ingredient") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", modifier = Modifier.clickable {active = false}) },
            trailingIcon = {
                if (active) {
                    Icon(Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier.clickable {if (text.isNotEmpty()) text = "" else active = false})
                }
            },
        ){
            Column(
                Modifier.verticalScroll(rememberScrollState())
            ){
                itemList.forEach { item ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp)
                        .padding(vertical = 15.dp)
                        .clickable { text = item; active = false }) {
                        Text(
                            text = item,
                        )
                    }
                }
            }

        }
    if (text.isNotEmpty() && !active && itemList.contains(text)) {
        Log.d("", "searchbar: $text")
        return text
    }else{
        return ""
    }
}








