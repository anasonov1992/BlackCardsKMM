package com.example.blackcardskmm.di

import com.example.blackcardskmm.data.database.FractionDbObject
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

fun databaseModule() = module {
    single {
        val config = RealmConfiguration.create(schema = setOf(FractionDbObject::class))
        Realm.open(config)
    }
}