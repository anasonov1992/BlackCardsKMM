package com.example.blackcardskmm.android.ui.navigation.components

import androidx.compose.animation.Crossfade
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.blackcardskmm.components.main.MainComponent.NavItem
import com.example.blackcardskmm.android.ui.theme.mikadanFont

@Composable
internal fun TabsBottomNavigation(
    items: List<NavItem>,
    onNavItemClick: (NavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigation(modifier = modifier) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    TabNavigationItemIcon(
                        item = item,
                        selected = item.selected
                    )
                },
                label = {
                    Text(text = item.type.title, fontFamily = mikadanFont)
                },
                selected = item.selected,
                onClick = { onNavItemClick(item) },
            )
        }
    }
}

@Composable
private fun TabNavigationItemIcon(item: NavItem, selected: Boolean) {
    //FIXME implement crossplatform KMM resource
//    val painter = painterResource(item.iconResId)
//    val selectedPainter = item.selectedIconResId?.let { painterResource(it) }
//
//    if (selectedPainter == null) {
//        Icon(
//            painter = painter,
//            contentDescription = stringResource(item.labelResId)
//        )
//    } else {
//        Crossfade(targetState = selected) {
//            Icon(
//                painter = if (it) selectedPainter else painter,
//                contentDescription = stringResource(item.labelResId)
//            )
//        }
//    }
}
