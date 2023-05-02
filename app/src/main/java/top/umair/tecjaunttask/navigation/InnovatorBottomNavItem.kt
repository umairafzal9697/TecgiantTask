package top.umair.tecjaunttask.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class InnovatorBottomNavItem(var title: String, var icon: ImageVector, var route: String) {

    object InnovatorBottomHomeScreen :
        InnovatorBottomNavItem("Home", Icons.Filled.Home, "innovator_home_screen")


    object InnovatorBottomAddInnovator :
        InnovatorBottomNavItem("AddInnovator", Icons.Filled.Person, "innovator_add")



}
