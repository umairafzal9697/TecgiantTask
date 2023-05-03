package top.umair.tecjaunttask

import top.umair.tecjaunttask.viewModel.InnovatorViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import top.umair.tecjaunttask.screens.InnovatorMainScreen
import top.umair.tecjaunttask.ui.theme.TecjauntTaskTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel : InnovatorViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MyApp(viewModel)
        }
    }
}

@Composable
fun MyApp(viewModel: InnovatorViewModel) {
    TecjauntTaskTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
                    InnovatorMainScreen(innovatorViewModel = viewModel)
            }
    }
}

