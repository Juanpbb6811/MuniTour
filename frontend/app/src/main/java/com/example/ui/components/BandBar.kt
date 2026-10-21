package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.RibbonColor1
import com.example.ui.theme.RibbonColor2
import com.example.ui.theme.RibbonColor3
import com.example.ui.theme.RibbonColor4

@Composable
fun BandBar(
    modifier: Modifier = Modifier,
    height: Dp = 6.dp
) {
    Row(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(height / 2))
    ) {
        Box(modifier = Modifier.weight(1f).fillMaxHeight().background(RibbonColor1))
        Box(modifier = Modifier.weight(1f).fillMaxHeight().background(RibbonColor2))
        Box(modifier = Modifier.weight(1f).fillMaxHeight().background(RibbonColor3))
        Box(modifier = Modifier.weight(1f).fillMaxHeight().background(RibbonColor4))
    }
}
