package com.example.blackcardskmm.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.example.blackcardskmm.components.auth.AuthComponent
import com.example.blackcardskmm.components.createDeck.CreateCardDeckComponent
import com.example.blackcardskmm.components.main.MainComponent
import com.example.blackcardskmm.components.register.RegisterComponent

class RootComponent internal constructor(
    componentContext: ComponentContext,
    private val auth: (ComponentContext, (AuthComponent.Output) -> Unit) -> AuthComponent,
    private val register: (ComponentContext, (RegisterComponent.Output) -> Unit) -> RegisterComponent,
    private val main: (ComponentContext, (MainComponent.Output) -> Unit) -> MainComponent,
    private val createDeck: (ComponentContext, (CreateCardDeckComponent.Output) -> Unit) -> CreateCardDeckComponent
): ComponentContext by componentContext {
    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory
    ) : this(
        componentContext = componentContext,
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
        createDeck = { childContext, output ->
            CreateCardDeckComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = output
            )
        }
    )

    private val navigation = StackNavigation<Configuration>()

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Configuration.Auth,
            handleBackButton = false,
            childFactory = ::createChild
        )

    val childStack: Value<ChildStack<*, Child>> = stack

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when (configuration) {
            is Configuration.Auth -> Child.Auth(auth(componentContext, ::onAuthOutput))
            is Configuration.Register -> Child.Register(register(componentContext, ::onRegisterOutput))
            is Configuration.Main -> Child.Main(main(componentContext, ::onMainOutput))
            is Configuration.CreateDeck -> Child.CreateDeck(createDeck(componentContext, ::onCreateDeckOutput))
        }

    private fun onAuthOutput(output: AuthComponent.Output): Unit =
        when (output) {
            AuthComponent.Output.NavigateToMain -> navigation.push(Configuration.Main)
            AuthComponent.Output.NavigateToRegister -> navigation.push(Configuration.Register)
        }

    private fun onRegisterOutput(output: RegisterComponent.Output): Unit = Unit

    private fun onMainOutput(output: MainComponent.Output): Unit =
        when (output) {
            is MainComponent.Output.NavigateToCreateCardDeck -> navigation.push(Configuration.CreateDeck)
            MainComponent.Output.NavigateToLogout -> navigation.replaceCurrent(Configuration.Auth)
        }

    //FIXME
    private fun onCreateDeckOutput(output: CreateCardDeckComponent.Output): Unit = Unit

    private sealed interface Configuration : Parcelable {
        @Parcelize
        object Auth: Configuration

        @Parcelize
        object Register: Configuration

        @Parcelize
        object Main : Configuration

        @Parcelize
        object CreateDeck : Configuration
    }

    sealed class Child {
        data class Auth(val component: AuthComponent) : Child()
        data class Register(val component: RegisterComponent) : Child()
        data class Main(val component: MainComponent) : Child()
        data class CreateDeck(val component: CreateCardDeckComponent) : Child()
    }
}