package com.example.blackcardskmm.android.ui.views.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.example.blackcardskmm.android.ui.views.cards.CardArtsList
import com.example.blackcardskmm.android.ui.views.decks.SetDeckNameBottomSheet
import com.example.blackcardskmm.android.ui.views.fractions.Fractions
import com.example.blackcardskmm.android.ui.views.fractions.FractionsBottomSheet
import com.example.blackcardskmm.components.main.MainComponent
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun Main(
    component: MainComponent
) {
    val state by component.state.collectAsStateWithLifecycle()

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val drawerGesturesEnabledState = remember { mutableStateOf(true) }

    val closeDrawer = remember {
        {
            coroutineScope.launch { scaffoldState.drawerState.close() }
        }
    }

    var bottomSheetType: BottomSheetType? by remember {
        mutableStateOf(null)
    }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.Expanded },
        skipHalfExpanded = true
    )

    val closeBottomSheet = {
        coroutineScope.launch { bottomSheetState.hide() }
    }

    val openBottomSheet = {
        coroutineScope.launch { bottomSheetState.show() }
    }

    //FIXME
//    backHandler(bottomSheetState.isVisible) {
//        coroutineScope.launch { bottomSheetState.hide() }
//    }

    var fractionId by rememberSaveable { mutableStateOf(0) }
    var deckName by rememberSaveable { mutableStateOf("") }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(16.dp),
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            bottomSheetType?.let {
                when(it) {
                    BottomSheetType.Fractions -> { }
                    //FIXME
//                        FractionsBottomSheet(
//                            fractions = state.fractions,
//                            closeBottomSheet = { it ->
//                                closeBottomSheet()
//                                fractionId = it
//                                bottomSheetType = BottomSheetType.DeckName
//                                openBottomSheet()
//                            }
//                    )
                    BottomSheetType.DeckName ->
                        SetDeckNameBottomSheet(
                            closeBottomSheet = { it ->
                                it?.let {
                                    deckName = it
                                    closeBottomSheet()
                                    component.onOutput(MainComponent.Output.NavigateToCreateCardDeck(fractionId, deckName))
                                }
                            }
                        )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            bottomBar = {
                BottomNavigationBar(
                    navItems = state.navItems,
                    onNavItemSelected = component::onNavItemClicked,
                    coroutineScope,
                    scaffoldState
                )
            },
            floatingActionButton = {
                CreateDeckFloatingActionButton(
                    onClick = {
                        bottomSheetType = BottomSheetType.Fractions
                        openBottomSheet()
                    }
                )
            },
            drawerGesturesEnabled = drawerGesturesEnabledState.value,
            drawerContent = {
                Drawer(
                    onLogoutClicked = {
                        closeDrawer()
                        component.onOutput(MainComponent.Output.NavigateToLogout)
                    }
                ) { navItem ->
                    closeDrawer()
                    //TODO: implement drawer navigation
//                    menuNavigator.navigate(navItem)
                }
            }
        ) { innerPadding ->
            Children(
                stack = component.childStack,
                animation = stackAnimation(fade()),
                modifier = Modifier.padding(innerPadding)
            ) {
                when (val child = it.instance) {
                    is MainComponent.Child.Fractions -> Fractions(child.component)
                    is MainComponent.Child.CardArts -> CardArtsList(child.component)
                }
            }
        }
    }
}

enum class BottomSheetType {
    Fractions,
    DeckName
}