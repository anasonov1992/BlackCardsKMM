package com.example.blackcardskmm.data.database

import com.example.blackcardskmm.data.primitives.FractionType
import io.realm.kotlin.types.RealmObject

data class FractionDbObject(
    val id: Int,
    val type: FractionType,
    val name: String,
    val description: String,
    val artUrl: String?,
    val logoUrl: String?
): RealmObject