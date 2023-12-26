package com.example.blackcardskmm.data.database.mappers

import com.example.blackcardskmm.data.database.FractionDbObject
import com.example.blackcardskmm.domain.models.Fraction

fun Fraction.toFractionDbObject() = FractionDbObject(id, type, name, description, artUrl, logoUrl)

fun FractionDbObject.toFraction() = Fraction(id, type, name, description, artUrl, logoUrl)