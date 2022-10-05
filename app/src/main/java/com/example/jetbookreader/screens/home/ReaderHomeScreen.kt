package com.example.jetbookreader.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetbookreader.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Google Books Viewer",
            navController = navController
        )
    }, floatingActionButton = {
        FABContent {}
    }) {
        Surface(modifier = Modifier.fillMaxSize()) {

        }
    }

}

@Composable
fun FABContent(onTap: (String) -> Unit) {
    FloatingActionButton(
        onClick = { onTap },
        shape = RoundedCornerShape(50.dp),
        backgroundColor = Color(0xFF92CBDF)// MaterialTheme.colors.background
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a Book",
            tint = Color.White// MaterialTheme.colors.onSecondary
        )
    }
}


@Composable
fun ReaderAppBar(
    title: String, showProfile: Boolean = true, navController: NavController
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfile) {
                    Image(imageVector = Icons.Default.Favorite,
                        contentDescription = "Logo Icon",
                        modifier = Modifier
                            .clickable { RoundedCornerShape(12.dp) }
                            .scale(0.6f))
                }
                Text(
                    text = title,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.width(150.dp))
            }
        },
        actions = {
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut().run {
                    navController.navigate(ReaderScreens.LoginScreen.name)
                }
            }) {
                Icon(imageVector = Icons.Filled.Logout, contentDescription = "Logout")
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}