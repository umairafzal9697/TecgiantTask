package top.umair.tecjaunttask.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import top.umair.tecjaunttask.screens.InnovatorAddScreen
import top.umair.tecjaunttask.screens.InnovatorHomeScreen
import top.umair.tecjaunttask.viewModel.InnovatorViewModel

@Composable
fun InnovatorNavigationGraph(navController: NavHostController, innovatorViewModel: InnovatorViewModel) {

    NavHost(navController = navController, startDestination = InnovatorBottomNavItem.InnovatorBottomHomeScreen.route) {

        composable(route = InnovatorBottomNavItem.InnovatorBottomHomeScreen.route) {

            InnovatorHomeScreen()
        }

        composable(route = InnovatorBottomNavItem.InnovatorBottomAddInnovator.route) {

            InnovatorAddScreen()
        }

    }


}
