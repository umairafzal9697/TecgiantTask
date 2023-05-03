package top.umair.tecjaunttask.screens


import android.widget.Toast
import top.umair.tecjaunttask.viewModel.InnovatorViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import top.umair.tecjaunttask.models.InnovatorEntity


@Composable
fun InnovatorHomeScreen(viewModel: InnovatorViewModel = hiltViewModel()) {
    val innovatorList =  viewModel.innovatorListState .collectAsState()

    val context = LocalContext.current


    Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = {
                    viewModel.backupData(){
                        if (it){
                            Toast.makeText(context, "Operation successful", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "operation failed", Toast.LENGTH_SHORT).show()

                        }
                    }
                },  shape = RoundedCornerShape(25.dp), modifier = Modifier
                    .padding(top = 15.dp)) {
                    Text(text = "Backup", color = Color.White)
                }
                Button(onClick = { viewModel.restoreData(){
                    if (it){
                        Toast.makeText(context, "Operation successful", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "operation failed", Toast.LENGTH_SHORT).show()

                    }
                } }, shape = RoundedCornerShape(25.dp), modifier = Modifier
                    .padding(top = 15.dp)) {
                    Text(text = "Restore Backup",color = Color.White)
                }

            }


        InnovatorList(innovators = innovatorList.value)
    }


}
@Composable
fun InnovatorList(innovators: List<InnovatorEntity>) {
    LazyColumn(modifier = Modifier.padding(2.dp),
        contentPadding = PaddingValues(1.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
       items(innovators){
           innovatorListItem(it)
       }
        }
    }

@Composable
private fun innovatorListItem(innovator: InnovatorEntity) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = Color(0xFFB1BBB4),
            shape = RoundedCornerShape(size = 14.dp)

        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = (innovator.firstName + " " + innovator.lastName) ?: "",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = innovator.age ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = innovator.college ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = innovator.country ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = innovator.description ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }






