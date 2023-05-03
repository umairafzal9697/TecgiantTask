package top.umair.tecjaunttask.navigation

import top.umair.tecjaunttask.viewModel.InnovatorViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import top.umair.tecjaunttask.screens.InnovatorAddScreen
import top.umair.tecjaunttask.screens.InnovatorHomeScreen

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
