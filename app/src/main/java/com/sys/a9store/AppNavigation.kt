package com.sys.a9store

import AddProductPage
import LoginScreen
import ProductsPage
import SignupScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.sys.a9store.screens.AuthScreen
import com.sys.a9store.screens.HomeScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
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
        composable("products") {
            ProductsPage(modifier)
        }
        composable("add-product") {
            AddProductPage(modifier)
        }

//        navigation(
//            startDestination = "products",
//            route = "products_tab"  // This is important
//        ) {
//            composable("products") { ProductsPage(modifier) }
//            composable("add-product") { AddProductPage(modifier) }
//        }

    }
}

object GlobalNavigation {
    lateinit var navController: NavHostController
}
