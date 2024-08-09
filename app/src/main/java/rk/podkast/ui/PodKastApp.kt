package rk.podkast.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import rk.podkast.R

@Serializable
data object MainNav

@Serializable
data object Main
@Serializable
object Home

@Serializable
data object Category

@Serializable
data object Favorite

@Serializable
data object Profile

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Scaffold(modifier = modifier, bottomBar = { AppNavigation() }) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = Home
        ) {
            composable<Home> {}
            composable<Profile> {}
            composable<Favorite> {}
            composable<Category> {}

        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    NavigationBar(modifier = modifier) {
        AppNavigationItem(
            selected = true, onClick = { /*TODO*/ }, iconRes = R.drawable.ic_home,
            label = "Home"
        )
        AppNavigationItem(
            selected = false, onClick = { /*TODO*/ }, iconRes =
            R.drawable.ic_category,
            label = "Category"
        )
        AppNavigationItem(
            selected = false, onClick = { /*TODO*/ }, iconRes =
            R.drawable.ic_favorite,
            label = "Favorite"
        )
        AppNavigationItem(
            selected = false,
            onClick = { /*TODO*/ },
            iconRes = R.drawable.ic_settings,
            label = "Setting"
        )
    }
}

@Composable
fun RowScope.AppNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    label: String? = null,
    @DrawableRes iconRes: Int
) {
    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent
        ),
        selected = selected, onClick = onClick, icon = {
            Icon(painter = painterResource(id = iconRes), contentDescription = "")
        },
        label = {
            label?.let {
                Text(text = it)
            }
        }

    )
}

fun NavGraphBuilder.mainGraph() {
    navigation<MainNav>(startDestination = Main) {
        composable<Main> {
            MainScreen()
        }
    }
}