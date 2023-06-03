package com.example.blackcardskmm.android.ui.navigation.models

sealed class Screen(
    val route: String,
    val title: String? = null
) {
    object Splash : Screen("splash")
    object SignIn : Screen("signIn")
    object Registration : Screen("registration")
    object Main : Screen("main")
    object Menu : Screen("menu")
    object CardArts : Screen("arts")
    object Fractions : Screen("fractions")
    object Lore : Screen("lore")
    object Decks : Screen("decks")
    object CardDeck : Screen("deck")
    object CreateCardDeck : Screen("createDeck")
    object CreateCardDeckSummary : Screen("createDeckSummary")
}