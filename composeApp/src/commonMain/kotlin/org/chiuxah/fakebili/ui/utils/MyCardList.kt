package org.chiuxah.fakebili.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyCard(modifier: Modifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 15.dp, vertical = 4.dp),containerColor : Color? = null,content: @Composable () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 1.75.dp),
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = if(containerColor == null) CardDefaults.cardColors() else CardDefaults.cardColors(containerColor = containerColor)
    ) {
        content()
    }
}


