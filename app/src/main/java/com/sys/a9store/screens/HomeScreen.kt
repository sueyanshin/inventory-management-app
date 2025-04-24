package com.sys.a9store.screens

import CartPage
import HomePage
import ProductsPage
import ProfilePage
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    val navItemList = listOf(
        NavItem("ပင်မ", Icons.Default.Home),
        NavItem("စျေးခြင်း", Icons.Default.ShoppingCart),
        NavItem("ကုန်ပစ္စည်းများ", Icons.Default.List),
        NavItem("အကောင့်", Icons.Default.Person),
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
                        Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                    },
                        label = {
                            Text(text = navItem.label)
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
        2 -> ProductsPage()
        3 -> ProfilePage()
    }

}


data class NavItem(
    val label: String,
    val icon: ImageVector
)