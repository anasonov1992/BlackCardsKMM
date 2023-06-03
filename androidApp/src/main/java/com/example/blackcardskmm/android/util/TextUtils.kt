package com.example.blackcardskmm.android.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.blackcardskmm.data.primitives.CardUniqueType
import com.example.blackcardskmm.data.primitives.FractionType
import com.example.blackcardskmm.data.primitives.SpellType
import com.example.blackcardskmm.data.primitives.UnitClassType
import com.example.blackcardskmm.domain.models.SpellTypeModel
import com.example.blackcardskmm.domain.models.UnitClass
import com.example.blackcardskmm.android.ui.theme.*

fun buildUnitClassesString(classes: List<UnitClass>): AnnotatedString = buildAnnotatedString {
    for (unitClass in classes) {
        withStyle(style = SpanStyle(color = getTextColorByUnitClassType(unitClass.type))) {
            append("${unitClass.displayName}. ")
        }
    }
}

fun buildSpellTypesString(types: List<SpellTypeModel>): AnnotatedString = buildAnnotatedString {
    for (spellType in types) {
        withStyle(style = SpanStyle(color = getTextColorBySpellType(spellType.type))) {
            append("${spellType.displayName}.\n")
        }
    }
}

fun getTextColorByFractionType(type: FractionType): Color = when(type) {
    FractionType.Tulin -> TulinColor
    else -> DefaultFractionColor
}

fun getTextColorByUniqueness(uniqueType: CardUniqueType): Color =
    when(uniqueType) {
        CardUniqueType.Unique -> UniqueCardColor
        CardUniqueType.NotMoreTwo -> NotMoreTwoCardsColor
        else -> DefaultCardColor
    }

fun getTextColorByUnitClassType(classType: UnitClassType) = when(classType) {
    UnitClassType.Man -> ManColor
    UnitClassType.Craftsman -> CraftsmanColor
    UnitClassType.Pleb -> PlebColor
    UnitClassType.Spy -> SpyColor
    UnitClassType.Gangster -> GangsterColor
    UnitClassType.Beast -> BeastColor
    UnitClassType.Gremlin -> GremlinColor
    else -> DefaultCardColor
}

fun getTextColorBySpellType(spellType: SpellType) = when(spellType) {
    SpellType.Starting -> DefaultCardColor
    SpellType.Protective -> ProtectiveSpellColor
    SpellType.Attack -> AttackSpellColor
    SpellType.Universal -> UniversalSpellColor
}