/*
 * Copyright 2025 Sample
 *
 * Licenced under the Apache License, Version 2.0 (the "Licence");
 * you may not use this file except in compliance with the Licence.
 * You may obtain a copy of the Licence at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package emmanuelmuturia.secretsscanner.home.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import emmanuelmuturia.secretsscanner.home.data.model.SampleHobby
import emmanuelmuturia.secretsscanner.home.ui.state.SecretsScannerHomeScreenUIState
import emmanuelmuturia.secretsscanner.home.ui.viewmodel.SecretsScannerHomeScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * This is the Home feature's Home Screen...
 */

class SecretsScannerHomeScreen() : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val secretsScannerHomeScreenViewModel = koinViewModel<SecretsScannerHomeScreenViewModel>()

        val secretsScannerHomeScreenUIState: SecretsScannerHomeScreenUIState by
            secretsScannerHomeScreenViewModel.secretsScannerHomeScreenUIState.collectAsStateWithLifecycle()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    modifier =
                        Modifier.semantics {
                            contentDescription = "Sample Home Screen Top App Bar..."
                        },
                    title = {
                        Text(
                            modifier =
                                Modifier.semantics {
                                    contentDescription = "Sample Home Screen Top App Bar Text..."
                                },
                            text = "Sample...",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 21.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    },
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor =
                                MaterialTheme.colorScheme.background,
                        ),
                )
            },
        ) { paddingValues ->
            SampleHomeScreenContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                secretsScannerHomeScreenUIState = secretsScannerHomeScreenUIState,
                secretsScannerHomeScreenViewModel = secretsScannerHomeScreenViewModel,
            )
        }
    }
}

@Composable
private fun SampleHomeScreenContent(
    modifier: Modifier,
    secretsScannerHomeScreenUIState: SecretsScannerHomeScreenUIState,
    secretsScannerHomeScreenViewModel: SecretsScannerHomeScreenViewModel,
) {
    val sampleHobbies: List<SampleHobby> =
        secretsScannerHomeScreenUIState.samples.flatMap { it.sampleHobbies ?: emptyList() }

    val navigator = LocalNavigator.currentOrThrow

    var sampleQuery by rememberSaveable { mutableStateOf(value = "") }

    AnimatedVisibility(visible = secretsScannerHomeScreenUIState.isLoading) {
        Box(
            modifier =
                modifier
                    .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onBackground,
                strokeWidth = 3.dp,
                trackColor = MaterialTheme.colorScheme.background,
            )
        }
    }

    AnimatedVisibility(visible = secretsScannerHomeScreenUIState.error != null) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            item {
                Text(text = "${secretsScannerHomeScreenUIState.error}...")
            }
        }
    }

    AnimatedVisibility(visible = secretsScannerHomeScreenUIState.samples.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            SampleSearchBar(
                sampleQuery = sampleQuery,
                onSampleQueryChange = { sampleQuery = it },
                onSearchSample = {
                    secretsScannerHomeScreenViewModel.searchBooks(sampleQuery = sampleQuery)
                },
            )

            Spacer(modifier = Modifier.height(height = 7.dp))

            LazyVerticalStaggeredGrid(
                modifier = Modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(count = 2),
                verticalItemSpacing = 3.dp,
                horizontalArrangement = Arrangement.spacedBy(space = 3.dp),
            ) {
                items(items = sampleHobbies) { sampleHobby ->
                    CoilImage(
                        modifier =
                            Modifier.clickable {
                                sampleHobby.sampleHobbyId?.let {
                                    navigator.push(
                                        SampleDetailsScreen(
                                            sampleHobbyId = it,
                                        ),
                                    )
                                }
                            },
                        imageModel = { sampleHobby.sampleHobbyThumbnail },
                        imageOptions =
                            ImageOptions(
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center,
                            ),
                    )
                }
            }
        }
    }

    AnimatedVisibility(visible = secretsScannerHomeScreenUIState.samples.isEmpty()) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            item {
                Text(text = "No Sample Hobbies Found...")
            }
        }
    }
}

@Composable
private fun SampleSearchBar(
    sampleQuery: String,
    onSampleQueryChange: (String) -> Unit,
    onSearchSample: (String) -> Unit,
) {
    OutlinedTextField(
        modifier =
            Modifier.semantics {
                contentDescription = "Sample Home Screen Search Bar..."
            },
        value = sampleQuery,
        onValueChange = onSampleQueryChange,
        trailingIcon = {
            Icon(
                modifier =
                    Modifier.clickable {
                        onSearchSample(sampleQuery)
                    },
                imageVector = Icons.Rounded.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Sample Home Screen Search Icon...",
            )
        },
        label = {
            Text(
                modifier =
                    Modifier.semantics {
                        contentDescription = "Sample Home Screen Search Bar Label..."
                    },
                text = "Enter Sample Query...",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
            )
        },
        shape = RoundedCornerShape(size = 21.dp),
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
            ),
    )
}