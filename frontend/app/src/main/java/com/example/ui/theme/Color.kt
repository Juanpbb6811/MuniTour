package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// HTML Root Colors (Exact CSS match)
val HtmlBgLight = Color(0xFFF1F5F4)
val HtmlFgLight = Color(0xFF0F2F36)
val HtmlMutedLight = Color(0xFF46666C)
val HtmlLineLight = Color(0xFFC7D6D5)
val HtmlPanelLight = Color(0xFFFFFFFF)

val HtmlTeal = Color(0xFF0F6C77)
val HtmlClay = Color(0xFFA2502B)
val HtmlCherry = Color(0xFFB80F2A)

// HTML Dark Scheme (:root[data-theme="dark"])
val HtmlBgDark = Color(0xFF0F2F36)
val HtmlFgDark = Color(0xFFF4EBDD)
val HtmlMutedDark = Color(0xFFA9C0C2)
val HtmlLineDark = Color(0xFF25525B)
val HtmlPanelDark = Color(0xFF15404A)
val HtmlTealDark = Color(0xFF5CC3CD)
val HtmlClayDark = Color(0xFFE58F63)
val HtmlCherryDark = Color(0xFFFF6B7C)

// 4-Color Band (HTML .band)
val BandColor1 = Color(0xFF2A9AA6)
val BandColor2 = Color(0xFF1B5A63)
val BandColor3 = Color(0xFFC4693F)
val BandColor4 = Color(0xFFC8102E)

// Screen-specific Themes from HTML (.t-teal, .t-clay, .t-cherry)
val ThemeTealBg = Color(0xFF123B44)
val ThemeTealTabbar = Color(0xFF0E2E35)
val ThemeTealActive = Color(0xFF5CC3CD)

val ThemeClayGradientTop = Color(0xFFC27650)
val ThemeClayGradientBottom = Color(0xFFA85B37)
val ThemeClayTabbar = Color(0xFF9A5232)
val ThemeClayActive = Color(0xFFFFE2B8)
val ThemeClaySheet = Color(0xFFFBF3E4)
val ThemeClayDishCard = Color(0xFFFFFFFF)
val ThemeClayDishPrice = Color(0xFFA6791A)

val ThemeCherryBg = Color(0xFFF4F4F4)
val ThemeCherryHead = Color(0xFFC8102E)
val ThemeCherryTabbar = Color(0xFFC8102E)
val ThemeCherryActive = Color(0xFFFFFFFF)

val StarGold = Color(0xFFF2B632)

// Direct Compatibility Aliases
val BrandTealDark = HtmlBgDark
val BrandTealPrimary = HtmlTeal
val BrandTealLight = HtmlTealDark
val BrandTealPanel = HtmlPanelDark
val BrandClay = HtmlClay
val BrandClayLight = HtmlClayDark
val BrandClayDark = Color(0xFF823C1B)
val BrandClaySoft = Color(0xFFFFF1D6)
val BrandButter = Color(0xFFFFE2B8)
val BrandCherry = HtmlCherry
val BrandCherryVibrant = ThemeCherryHead
val BrandCherryLight = HtmlCherryDark
val RibbonColor1 = BandColor1
val RibbonColor2 = BandColor2
val RibbonColor3 = BandColor3
val RibbonColor4 = BandColor4
