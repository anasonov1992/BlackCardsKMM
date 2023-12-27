package com.example.blackcardskmm.android.ui.views.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.components.main.MainComponent.NavItem

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    navItems: List<NavItem>,
    onLogoutClicked: () -> Unit,
    onDestinationClicked: (route: NavItem) -> Unit
) {
    Box(
        modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp)
    ) {
        //FIXME
//        AvatarImage(
//            size = 100.dp,
//            avatarUrl = , //TODO fill from user profile
//            placeholderResource = R.drawable.ic_user_avatar
//        )
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            navItems.forEach { navItem ->
                Text(
                    text = navItem.type.title,
                    style = MaterialTheme.typography.h4.copy(fontFamily = mikadanFont),
                    modifier = Modifier.clickable {
                        onDestinationClicked(navItem)
                    }
                )
                Spacer(Modifier.height(24.dp))
            }
        }
        Text(
            text = "Выйти",
            style = MaterialTheme.typography.h4.copy(fontFamily = mikadanFont),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .clickable {
                    onLogoutClicked()
                }
        )
    }
}