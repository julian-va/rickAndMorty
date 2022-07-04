package com.example.rickandmorty.ui.component.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickandmorty.R
import com.example.rickandmorty.data.Result
import com.example.rickandmorty.domain.model.Characters
import com.example.rickandmorty.ui.component.Screen
import com.example.rickandmorty.ui.component.home.component.CharacterItems
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    onItemClicked: (Int) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state = homeViewModel.state
    val eventFlow = homeViewModel.evenFlow
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is HomeViewModel.UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            HomeTopBar()
        },
        bottomBar = {
            HomeBottomBar(
                showPrevius = state.showPrevious,
                showNext = state.showNext,
                onPreviousPress = { homeViewModel.getCharacter(increase = false) },
                onNextPressed = { homeViewModel.getCharacter(increase = true) }
            )
        }
    ) { innerPadding ->
        HomeContent(
            modifier = Modifier.padding(innerPadding),
            onItemClick = { onItemClicked(it) },
            isLoading = state.isLoading,
            characteres = state.character
        )
    }
}

@Composable
private fun HomeTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.home_title),
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        backgroundColor = MaterialTheme.colors.surface
    )
}

@Composable
private fun HomeBottomBar(
    showPrevius: Boolean,
    showNext: Boolean,
    onPreviousPress: () -> Unit,
    onNextPressed: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                modifier = Modifier
                    .weight(weight = 1f)
                    .height(height = 48.dp),
                enabled = showPrevius,
                onClick = onPreviousPress
            ) {
                Text(text = stringResource(id = R.string.previous_button))
            }
            TextButton(
                modifier = Modifier
                    .weight(weight = 1f)
                    .height(height = 48.dp),
                enabled = showNext,
                onClick = onNextPressed
            ) {
                Text(text = stringResource(id = R.string.next_button))
            }
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    isLoading: Boolean = false,
    characteres: List<Characters> = emptyList()
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.surface
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 6.dp),
            modifier = Modifier.fillMaxWidth(),
            content = {
                items(count = characteres.size) { index ->
                    CharacterItems(
                        modifier = Modifier.fillMaxWidth(),
                        item = characteres[index],
                        onItemClicked = { onItemClick(it) })
                }
            }
        )
        if (isLoading) FullScreenLoading()
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}