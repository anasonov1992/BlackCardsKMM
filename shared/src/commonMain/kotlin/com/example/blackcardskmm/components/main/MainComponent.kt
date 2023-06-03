package com.example.blackcardskmm.components.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnPause
import com.arkivanov.essenty.lifecycle.doOnResume
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.blackcardskmm.components.cards.CardArtsComponent
import com.example.blackcardskmm.components.fractions.FractionsComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class MainComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val fractions: (ComponentContext, (FractionsComponent.Output) -> Unit) -> FractionsComponent,
    private val cardArts: (ComponentContext, (CardArtsComponent.Output) -> Unit) -> CardArtsComponent,
    private val output: (Output) -> Unit
) : ComponentContext by componentContext {
    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
        output: (Output) -> Unit
    ) : this(
        componentContext = componentContext,
        storeFactory = storeFactory,
        fractions = { childContext, childOutput ->
            FractionsComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = childOutput
            )
        },
        cardArts = { childContext, childOutput ->
            CardArtsComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = childOutput
            )
        },
        output = output
    )

    private val store = instanceKeeper.getStore {
        MainStoreFactory(
            storeFactory = storeFactory
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<MainStore.State> = store.stateFlow

    private val navigation = StackNavigation<Configuration>()

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Configuration.Fractions,
            handleBackButton = false,
            childFactory = ::createChild
        )

    val childStack: Value<ChildStack<*, Child>> = stack

    private val stackSubscriber: (ChildStack<Configuration, Child>) -> Unit = {
        val intent = MainStore.Intent.SelectNavItem(
            when (it.active.instance) {
                is Child.Fractions -> NavItem.Type.FRACTIONS
                is Child.CardArts -> NavItem.Type.CARDARTS
            }
        )
        store.accept(intent)
    }

    init {
        lifecycle.doOnResume {
            stack.subscribe(stackSubscriber)
        }
        lifecycle.doOnPause {
            stack.unsubscribe(stackSubscriber)
        }
    }

    fun onNavItemClicked(item: NavItem) {
        if (item.type != NavItem.Type.MENU) {
            navigation.bringToFront(
                when (item.type) {
                    NavItem.Type.FRACTIONS -> Configuration.Fractions
                    else -> Configuration.CardArts
                }
            )
        }
    }

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when (configuration) {
            is Configuration.Fractions -> Child.Fractions(fractions(componentContext, ::onFractionsOutput))
            is Configuration.CardArts -> Child.CardArts(cardArts(componentContext, ::onCardArtsOutput))
        }

    //FIXME
    private fun onFractionsOutput(output: FractionsComponent.Output): Unit = Unit

    //FIXME
    private fun onCardArtsOutput(output: CardArtsComponent.Output): Unit = Unit

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class NavigateToCreateCardDeck(val fractionId: Int, val deckName: String) : Output()
        object NavigateToLogout : Output()
    }

    private sealed interface Configuration : Parcelable {
        @Parcelize
        object Fractions: Configuration

        @Parcelize
        object CardArts : Configuration
    }

    sealed class Child {
        data class Fractions(val component: FractionsComponent) : Child()
        data class CardArts(val component: CardArtsComponent) : Child()
    }

    data class NavItem(val selected: Boolean, val type: Type) {
        enum class Type(val id: Long, val title: String) {
            MENU(1L, "Меню"),
            FRACTIONS(2L, "Фракции"),
            CARDARTS(4L, "Арты")
        }
    }
}