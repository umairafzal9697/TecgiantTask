package top.umair.tecjaunttask.screens


import top.umair.tecjaunttask.viewModel.InnovatorViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import top.umair.tecjaunttask.models.InnovatorEntity


@Composable
fun InnovatorHomeScreen(viewModel: InnovatorViewModel = hiltViewModel()) {
    val innovatorList by viewModel.innovatorListState.collectAsState()


    Column {
        // Show loading indicator if data is being loaded
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = {
                    viewModel.backupData()
                }) {
                    Text(text = "Backup")
                }
                Button(onClick = { viewModel.restoreData() }) {
                    Text(text = "Restore Backup")
                }

            }
        InnovatorList(innovators = innovatorList)
    }


}
@Composable
fun InnovatorList(innovators: List<InnovatorEntity>) {
    LazyColumn {
        items(innovators) { innovator ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Column {
                    Text(
                        text = innovator.firstName ?: "",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = innovator.lastName ?: "",
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}




