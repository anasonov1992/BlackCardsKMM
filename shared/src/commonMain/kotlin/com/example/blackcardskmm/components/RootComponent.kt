package com.example.blackcardskmm.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.example.blackcardskmm.components.auth.AuthComponent
import com.example.blackcardskmm.components.cards.CardArtDetailComponent
import com.example.blackcardskmm.components.cards.CardsLibraryComponent
import com.example.blackcardskmm.components.createDeck.CreateCardDeckComponent
import com.example.blackcardskmm.components.decks.DecksComponent
import com.example.blackcardskmm.components.lore.LoreComponent
import com.example.blackcardskmm.components.main.MainComponent
import com.example.blackcardskmm.components.register.RegisterComponent
import com.example.blackcardskmm.components.startup.StartupComponent
import com.example.blackcardskmm.data.primitives.FractionType

class RootComponent internal constructor(
    componentContext: ComponentContext,
    private val startup: (ComponentContext, (StartupComponent.Output) -> Unit) -> StartupComponent,
    private val auth: (ComponentContext, (AuthComponent.Output) -> Unit) -> AuthComponent,
    private val register: (ComponentContext, (RegisterComponent.Output) -> Unit) -> RegisterComponent,
    private val main: (ComponentContext, (MainComponent.Output) -> Unit) -> MainComponent,
    private val lore: (ComponentContext, (LoreComponent.Output) -> Unit) -> LoreComponent,
    private val decks: (ComponentContext, (DecksComponent.Output) -> Unit) -> DecksComponent,
    private val deck: (ComponentContext, fractionId: Int, deckId: Int?, (CreateCardDeckComponent.Output) -> Unit) -> CreateCardDeckComponent,
    private val createDeck: (ComponentContext, fractionId: Int, deckId: Int?, deckName: String?, (CreateCardDeckComponent.Output) -> Unit) -> CreateCardDeckComponent,
    private val cardsLibrary: (ComponentContext, fractionId: Int, fractionType: FractionType, (CardsLibraryComponent.Output) -> Unit) -> CardsLibraryComponent,
    private val cardArtDetail: (ComponentContext, artId: Int, (CardArtDetailComponent.Output) -> Unit) -> CardArtDetailComponent
): ComponentContext by componentContext {
    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory
    ) : this(
        componentContext = componentContext,
        startup = { childContext, output ->
            StartupComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = output
            )
        },
        auth = { childContext, output ->
            AuthComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = output
            )
        },
        register = { childContext, output ->
            RegisterComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = output
            )
        },
        main = { childContext, output ->
            MainComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = output
            )
        },
        lore = { childContext, output ->
            LoreComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = output
            )
        },
        decks = { childContext, output ->
            DecksComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = output
            )
        },
        deck = { childContext, fractionId, deckId, output ->
            CreateCardDeckComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                fractionId = fractionId,
                deckId = deckId,
                output = output
            )
        },
        createDeck = { childContext, fractionId, deckId, deckName, output ->
            CreateCardDeckComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                fractionId = fractionId,
                deckId = deckId,
                deckName = deckName,
                output = output
            )
        },
        cardsLibrary = { childContext, fractionId, fractionType, output ->
            CardsLibraryComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                fractionId = fractionId,
                fractionType = fractionType,
                output = output
            )
        },
        cardArtDetail = { childContext, artId, output ->
            CardArtDetailComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                artId = artId,
                output = output
            )
        }
    )

    private val navigation = StackNavigation<Configuration>()

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Configuration.Startup,
            handleBackButton = false,
            childFactory = ::createChild
        )

    val childStack: Value<ChildStack<*, Child>> = stack

    private fun createChild(config: Configuration, componentContext: ComponentContext): Child =
        when (config) {
            is Configuration.Startup -> Child.Startup(startup(componentContext, ::onStartCoverOutput))
            is Configuration.Auth -> Child.Auth(auth(componentContext, ::onAuthOutput))
            is Configuration.Register -> Child.Register(register(componentContext, ::onRegisterOutput))
            is Configuration.Main -> Child.Main(main(componentContext, ::onMainOutput))
            is Configuration.Lore -> Child.Lore(lore(componentContext, ::onLoreOutput))
            is Configuration.Decks -> Child.Decks(decks(componentContext, ::onDecksOutput))
            is Configuration.Deck -> Child.Deck(deck(componentContext, config.fractionId, config.deckId, ::onDeckOutput))
            is Configuration.CreateCardDeck -> Child.CreateCardDeck(createDeck(componentContext, config.fractionId, config.deckId, config.deckName, ::onCreateCardDeckOutput))
            is Configuration.CardsLibrary -> Child.CardsLibrary(cardsLibrary(componentContext, config.fractionId, config.fractionType, ::onCreateCardsLibraryOutput))
            is Configuration.CardArtDetail -> Child.CardArtDetail(cardArtDetail(componentContext, config.artId, ::onCardArtDetailOutput))
        }

    private  fun onStartCoverOutput(output: StartupComponent.Output): Unit =
        when(output) {
            is StartupComponent.Output.NavigateToAuth -> navigation.replaceCurrent(Configuration.Auth)
            is StartupComponent.Output.NavigateToMain -> navigation.replaceCurrent(Configuration.Main)
        }

    private fun onAuthOutput(output: AuthComponent.Output): Unit =
        when (output) {
            AuthComponent.Output.NavigateToMain -> navigation.replaceCurrent(Configuration.Main)
            AuthComponent.Output.NavigateToRegister -> navigation.push(Configuration.Register)
        }

    private fun onRegisterOutput(output: RegisterComponent.Output): Unit =
        when (output) {
            RegisterComponent.Output.NavigateToMain -> navigation.push(Configuration.Main)
            RegisterComponent.Output.NavigateBack -> navigation.pop()
        }

    private fun onMainOutput(output: MainComponent.Output): Unit =
        when (output) {
            is MainComponent.Output.NavigateToCreateCardDeck -> navigation.push(Configuration.CreateCardDeck(output.fractionId, output.deckId, output.deckName))
            is MainComponent.Output.NavigateToCardsLibrary -> navigation.push(Configuration.CardsLibrary(output.fractionId, output.fractionType))
            is MainComponent.Output.NavigateToCardArtDetail -> navigation.push(Configuration.CardArtDetail(output.artId))
            MainComponent.Output.NavigateToLore -> navigation.push(Configuration.Lore)
            MainComponent.Output.NavigateToDecks -> navigation.push(Configuration.Decks)
            MainComponent.Output.NavigateToLogout -> navigation.replaceCurrent(Configuration.Auth)
        }

    private fun onLoreOutput(output: LoreComponent.Output): Unit =
        when (output) {
            LoreComponent.Output.NavigateBack -> navigation.pop()
        }

    private fun onDecksOutput(output: DecksComponent.Output): Unit =
        when (output) {
            DecksComponent.Output.NavigateBack -> navigation.pop()
            is DecksComponent.Output.NavigateToDeck -> navigation.push(Configuration.Deck(output.fractionId, output.deckId))
        }

    private fun onDeckOutput(output: CreateCardDeckComponent.Output): Unit =
        when (output) {
            is CreateCardDeckComponent.Output.NavigateBack -> navigation.pop()
        }

    private fun onCreateCardDeckOutput(output: CreateCardDeckComponent.Output): Unit =
        when (output) {
            is CreateCardDeckComponent.Output.NavigateBack -> navigation.pop()
        }

    private fun onCreateCardsLibraryOutput(output: CardsLibraryComponent.Output): Unit =
        when (output) {
            is CardsLibraryComponent.Output.NavigateBack -> navigation.pop()
        }

    private fun onCardArtDetailOutput(output: CardArtDetailComponent.Output): Unit =
        when (output) {
            CardArtDetailComponent.Output.NavigateBack -> navigation.pop()
        }


    private sealed class Configuration : Parcelable {
        @Parcelize
        data object Startup : Configuration()

        @Parcelize
        data object Auth : Configuration()

        @Parcelize
        data object Register : Configuration()

        @Parcelize
        data object Main : Configuration()

        @Parcelize
        data object Lore : Configuration()

        @Parcelize
        data object Decks : Configuration()

        @Parcelize
        data class Deck(val fractionId: Int, val deckId: Int) : Configuration()

        @Parcelize
        data class CreateCardDeck(val fractionId: Int, val deckId: Int? = null, val deckName: String? = null) : Configuration()

        @Parcelize
        data class CardsLibrary(val fractionId: Int, val fractionType: FractionType) : Configuration()

        @Parcelize
        data class CardArtDetail(val artId: Int) : Configuration()
    }

    sealed class Child {
        data class Startup(val component: StartupComponent) : Child()

        data class Auth(val component: AuthComponent) : Child()

        data class Register(val component: RegisterComponent) : Child()

        data class Main(val component: MainComponent) : Child()

        data class Lore(val component: LoreComponent) : Child()

        data class Decks(val component: DecksComponent) : Child()

        data class Deck(val component: CreateCardDeckComponent) : Child()

        data class CreateCardDeck(val component: CreateCardDeckComponent) : Child()

        data class CardsLibrary(val component: CardsLibraryComponent) : Child()

        data class CardArtDetail(val component: CardArtDetailComponent) : Child()
    }
}