package com.example.finalproject.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun TeamBadge(
    badgeUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val fallback: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Filled.SportsSoccer,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(40.dp),
        )
    }
    if (badgeUrl.isBlank()) {
        fallback()
    } else {
        SubcomposeAsyncImage(
            model = badgeUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            loading = { fallback() },
            error = { fallback() },
            modifier = modifier,
        )
    }
}
