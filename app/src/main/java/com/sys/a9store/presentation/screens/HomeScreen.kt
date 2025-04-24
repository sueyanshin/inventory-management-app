package com.sys.a9store.presentation.screens

import CartPage
import HomePage
import ProductsPage
import ProfilePage
import ScanCode
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sys.a9store.BarCodeScreen
import com.sys.a9store.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {

    val navItemList = listOf(
        NavItem(R.string.home, "home", Icons.Default.Home),
        NavItem(R.string.cart, "cart", Icons.Default.ShoppingCart),
        NavItem(R.string.scan, "scan", Icons.Default.Search),
        NavItem(R.string.products, "products", Icons.Default.List),
        NavItem(R.string.profile, "profile", Icons.Default.Person),
    )
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    Scaffold(
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(selected = index == selectedIndex, onClick = {
                        selectedIndex = index
                    }, icon = {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = stringResource(navItem.title)
                        )
                    },
                        label = {
                            Text(stringResource(navItem.title))
                        }
                    )

                }
            }
        }
    ) {
        ContentScreen(modifier = modifier.padding(it), selectedIndex)
    }
}


@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    when (selectedIndex) {
        0 -> HomePage()
        1 -> CartPage()
        2 -> BarCodeScreen(modifier)
        3 -> ProductsPage()
        4 -> ProfilePage()
    }

}

data class NavItem(
    @StringRes val title: Int,
    val route: String,
    val icon: ImageVector
)