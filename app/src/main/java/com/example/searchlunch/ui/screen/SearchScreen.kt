package com.example.searchlunch.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.searchlunch.R
import com.example.searchlunch.ui.AppViewModelProvider
import com.example.searchlunch.ui.SearchLunchTopAppBar
import com.example.searchlunch.ui.naviagtion.NavigationDestination
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object SearchScreenDestination: NavigationDestination {
    override val route: String = "search"
    override val titleRes: Int = R.string.search
}

enum class PermissionType {
    FINE_LOCATION,
    COARSE_LOCATION,
    NONE_LOCATION
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Suppress("functionName")
@Composable
fun SearchScreen(
    navigateUp: () -> Unit,
    navigateToSearchResult: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.factory)
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val searchSheetUiState = viewModel.searchSheetUiState
    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    var openedSearchScreen by remember { mutableIntStateOf(0) }
    LaunchedEffect(openedSearchScreen) {
        if (openedSearchScreen == 0) {
            openedSearchScreen += 1
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        topBar = {
            SearchLunchTopAppBar(
                title = stringResource(SearchScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        },
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 32.dp, end = 32.dp)
                .imePadding()
                .imeNestedScroll()
                .clickable(
                    interactionSource = null,
                    indication = null
                ) {
                    coroutineScope.launch {
                        keyboardController?.hide()
                        delay(100)
                        focusManager.clearFocus()
                    }
                }
        ) {
            SearchList(
                bottomSheetUiState = searchSheetUiState,
                focusRequester = focusRequester,
                updateBottomSheetUiState = viewModel::updateUserPreferencesRepository,
                modifier = Modifier.weight(1F)
            )
            Button(
                onClick = {
                    scope.launch(Dispatchers.IO) {
                        val permissionType: PermissionType = when {
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ->
                                PermissionType.FINE_LOCATION
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ->
                                PermissionType.COARSE_LOCATION
                            else -> PermissionType.NONE_LOCATION
                        }

                        if(permissionType != PermissionType.NONE_LOCATION) {
                            val priority = if (permissionType == PermissionType.FINE_LOCATION) {
                                Priority.PRIORITY_HIGH_ACCURACY
                            } else {
                                Priority.PRIORITY_BALANCED_POWER_ACCURACY
                            }
                            Log.d("location", "isChecking")
                            val result: Location? = locationClient.getCurrentLocation(
                                priority,
                                CancellationTokenSource().token,
                            ).await()
                            val latitude = result?.latitude
                            val longitude = result?.longitude
                            Log.d("location", "$latitude, $longitude")
                            if(latitude != null && longitude != null) {
                                Log.d("location","isNotNull")
                                viewModel.updateUserPreferencesRepository(
                                    EditType.KEYWORD,
                                    searchSheetUiState.keyword
                                )
                                viewModel.updateUserPreferencesRepository(
                                    EditType.RANGE,
                                    searchSheetUiState.range.toString()
                                )
                                viewModel.updateLatAndLng(latitude, longitude)
                            }
                        }
                    }
                    navigateToSearchResult()
                },
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.search))
            }
        }
    }
}

@Suppress("functionName")
@Composable
fun SearchList(
    bottomSheetUiState: SearchSheetUiState,
    focusRequester: FocusRequester,
    updateBottomSheetUiState: (EditType, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        EditContent(
            keyword = bottomSheetUiState.keyword,
            focusRequester = focusRequester,
            updateBottomSheetUiState = updateBottomSheetUiState
        )
        HorizontalDivider()
        RadioButtonList(
            rangeValue = bottomSheetUiState.range,
            updateUserPreferencesRepository = updateBottomSheetUiState
        )
    }
}

@Suppress("functionName")
@Composable
fun RadioButtonList(
    modifier: Modifier = Modifier,
    rangeValue: Int,
    updateUserPreferencesRepository: (EditType, String) -> Unit
) {
    val radioOptions = listOf("300m", "500m", "1000m", "2000m", "3000m")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[rangeValue - 1]) }

    Column(
        modifier = modifier.selectableGroup()
    ) {
        Text(
            text = stringResource(R.string.distanceFromCurrentLocation),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            Log.d("radio","${radioOptions.indexOf(text) + 1}")
                            updateUserPreferencesRepository(EditType.RANGE, (radioOptions.indexOf(text) + 1).toString())
                        },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Suppress("functionName")
@Composable
fun EditContent(
    keyword: String,
    focusRequester: FocusRequester,
    updateBottomSheetUiState: (EditType, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d("SearchScreen", "SearchSheetUiState: $keyword")
    val currentValue by rememberUpdatedState(keyword)

    // selectionで指定することで、カーソルを文字の末尾に常に持ってくる
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = currentValue,
                selection = TextRange(keyword.length)
            )
        )
    }

    OutlinedTextField(
        value = textFieldValue,
        placeholder = {
            Text(
                text= stringResource(R.string.keywordPlaceHolder)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search)
            )
        },
        onValueChange = {
            textFieldValue = it
            updateBottomSheetUiState(EditType.KEYWORD, it.text)
        },
        modifier = modifier
            .sizeIn(minWidth = 600.dp)
            .focusRequester(focusRequester)
    )
}