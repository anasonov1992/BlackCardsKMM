package com.example.blackcardskmm.android.di

import com.example.blackcardskmm.android.ui.vm.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun vmModule(): Module = module {
    viewModel { AuthViewModel(get(), get(), get()) }
    viewModel { RegisterViewModel(get(), get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { LoreViewModel(get(), get()) }
    viewModel { FractionsViewModel(get()) }
    viewModel { DecksViewModel(get()) }
    viewModel { CardArtsViewModel(get()) }
    viewModel { CardArtDetailViewModel(get(), get()) }
    viewModel { CardsLibraryViewModel(get(), get()) }
    viewModel { CreateCardDeckViewModel(get(), get()) }
    viewModel { CardDeckViewModel(get()) }
    viewModel { CardImageDetailViewModel(get()) }
}