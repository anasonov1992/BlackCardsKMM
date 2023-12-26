package com.example.blackcardskmm.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.example.blackcardskmm.components.auth.AuthComponent
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
    private val createDeck: (ComponentContext, (CreateCardDeckComponent.Output) -> Unit) -> CreateCardDeckComponent,
    private val cardsLibrary: (ComponentContext, fractionId: Int, fractionType: FractionType, (CardsLibraryComponent.Output) -> Unit) -> CardsLibraryComponent
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
        createDeck = { childContext, output ->
            CreateCardDeckComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
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

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when (configuration) {
            is Configuration.Startup -> Child.Startup(startup(componentContext, ::onStartCoverOutput))
            is Configuration.Auth -> Child.Auth(auth(componentContext, ::onAuthOutput))
            is Configuration.Register -> Child.Register(register(componentContext, ::onRegisterOutput))
            is Configuration.Main -> Child.Main(main(componentContext, ::onMainOutput))
            is Configuration.Lore -> Child.Lore(lore(componentContext, ::onLoreOutput))
            is Configuration.Decks -> Child.Decks(decks(componentContext, ::onDecksOutput))
            is Configuration.CreateCardDeck -> Child.CreateCardDeck(createDeck(componentContext, ::onCreateCardDeckOutput))
            is Configuration.CardsLibrary -> Child.CardsLibrary(cardsLibrary(componentContext, configuration.fractionId, configuration.fractionType, ::onCreateCardsLibraryOutput))
        }

    private  fun onStartCoverOutput(output: StartupComponent.Output): Unit =
        when(output) {
            is StartupComponent.Output.NavigateToAuth -> navigation.replaceCurrent(Configuration.Auth)
            is StartupComponent.Output.NavigateToAppStart -> navigation.replaceCurrent(Configuration.Main)
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
            is MainComponent.Output.NavigateToCreateCardDeck -> navigation.push(Configuration.CreateCardDeck)
            is MainComponent.Output.NavigateToCardsLibrary -> navigation.push(Configuration.CardsLibrary(output.fractionId, output.fractionType))
            MainComponent.Output.NavigateToLogout -> navigation.replaceCurrent(Configuration.Auth)
        }

    private  fun onLoreOutput(output: LoreComponent.Output): Unit =
        when (output) {
            LoreComponent.Output.NavigateBack -> navigation.pop()
        }

    private  fun onDecksOutput(output: DecksComponent.Output): Unit =
        when (output) {
            DecksComponent.Output.NavigateBack -> navigation.pop()
        }

    //FIXME
    private fun onCreateCardDeckOutput(output: CreateCardDeckComponent.Output): Unit = Unit

    //FIXME
    private fun onCreateCardsLibraryOutput(output: CardsLibraryComponent.Output): Unit = Unit
//        when (output) {
//            is CardsLibraryComponent.Output.NavigateToCardDetail -> navigation.push(Configuration.CardDetail)
//            is CardsLibraryComponent.Output.NavigateBack -> navigation.pop()
//        }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Startup: Configuration()

        @Parcelize
        object Auth: Configuration()

        @Parcelize
        object Register: Configuration()

        @Parcelize
        object Main : Configuration()

        @Parcelize
        object Lore: Configuration()

        @Parcelize
        object Decks: Configuration()

        @Parcelize
        object CreateCardDeck : Configuration()

        @Parcelize
        data class CardsLibrary(val fractionId: Int, val fractionType: FractionType) : Configuration()
    }

    sealed class Child {
        data class Startup(val component: StartupComponent) : Child()

        data class Auth(val component: AuthComponent) : Child()

        data class Register(val component: RegisterComponent) : Child()

        data class Main(val component: MainComponent) : Child()

        data class Lore(val component: LoreComponent) : Child()

        data class Decks(val component: DecksComponent) : Child()

        data class CreateCardDeck(val component: CreateCardDeckComponent) : Child()

        data class CardsLibrary(val component: CardsLibraryComponent) : Child()
    }
}