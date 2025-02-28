package com.example.searchlunch.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.searchlunch.R

@Suppress("functionName")
@Composable
fun <T> OriginalCardRow(
    modifier: Modifier = Modifier,
    titleResId: Int,
    itemList: List<T>,
    keyGenerator: (T) -> Any,
    onTextButtonClick: () -> Unit,
    contentCard: @Composable (T) -> Unit,
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(titleResId),
            style = MaterialTheme.typography.titleLarge
        )
        LazyRow(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
        ) {
            items(
                items = itemList,
                key = {keyGenerator(it)}
            ) { quickSearch ->
                contentCard(
                    quickSearch
                )
            }
        }
        TextButton(
            onClick = onTextButtonClick,
            modifier = Modifier
                .align(Alignment.End)
        ) {
            Text(
                text = stringResource(R.string.allShow)
            )
        }
    }
}