package com.sys.a9store.presentation.navigation

import AddProductPage
import LoginScreen
import ProductsPage
import SignupScreen
import android.graphics.drawable.Icon
import androidx.annotation.StringRes
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.sys.a9store.R
import com.sys.a9store.presentation.screens.AuthScreen
import com.sys.a9store.presentation.screens.HomeScreen

sealed class Screen(val route:String,@StringRes val title: Int, val icon: ImageVector){
    object  Home : Screen("home", R.string.home, Icons.Default.Home)
    object  Sales : Screen("sales", R.string.sales,Icons.Default.List)
    object  Scan : Screen("scan", R.string.scan,Icons.Default.Home)
    object  Cart : Screen("cart", R.string.cart,Icons.Default.ShoppingCart)
    object  Products : Screen("products", R.string.products,Icons.Default.List)
    object  Profile : Screen("home", R.string.profile,Icons.Default.Person)
}

@Composable
fun MainNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    GlobalNavigation.navController = navController

    val isLoggedIn = Firebase.auth.currentUser != null
    val firstPage = if (isLoggedIn) "home" else "auth"

    NavHost(navController = navController, startDestination = firstPage) {
        composable("auth") {
            AuthScreen(
                modifier, navController
            )
        }
        composable("login") {
            LoginScreen(modifier, navController)
        }
        composable("signup") {
            SignupScreen(modifier, navController)
        }
        composable("home") {
            HomeScreen(modifier, navController)
        }
        composable("add-product") {
            AddProductPage(modifier)
        }

        composable(Screen.Products.route){
            ProductsPage(modifier)
        }

    }
}

object GlobalNavigation {
    lateinit var navController: NavHostController
}

