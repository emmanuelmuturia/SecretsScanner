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
package emmanuelmuturia.sample.home.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import emmanuelmuturia.sample.home.ui.state.SampleDetailsScreenUIState
import emmanuelmuturia.sample.home.ui.viewmodel.SampleDetailsScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * This is the Home feature's Details Screen...
 */

data class SampleDetailsScreen(
    val sampleHobbyId: String,
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val sampleDetailsScreenViewModel = koinViewModel<SampleDetailsScreenViewModel>()

        val sampleDetailsScreenUIState by
            sampleDetailsScreenViewModel.sampleDetailsScreenUIState.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = sampleHobbyId) {
            sampleDetailsScreenViewModel.getSampleById(sampleId = sampleHobbyId)
        }

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    modifier =
                        Modifier.semantics {
                            contentDescription = "Sample Details Screen Top App Bar..."
                        },
                    title = {
                        sampleDetailsScreenUIState.sampleHobby?.sampleHobbyName?.let {
                            Text(
                                modifier =
                                    Modifier.semantics {
                                        contentDescription =
                                            "Sample Details Screen Top App Bar Text..."
                                    }.padding(end = 10.dp),
                                text = it,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 21.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    },
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor =
                                MaterialTheme.colorScheme.background,
                        ),
                    navigationIcon = {
                        IconButton(onClick = {
                            navigator.pop()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Arrow Back...",
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    },
                )
            },
        ) { paddingValues ->
            SampleDetailsScreenContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                sampleDetailsScreenUIState = sampleDetailsScreenUIState,
            )
        }
    }
}

@Composable
private fun SampleDetailsScreenContent(
    modifier: Modifier,
    sampleDetailsScreenUIState: SampleDetailsScreenUIState,
) {
    AnimatedVisibility(visible = sampleDetailsScreenUIState.isLoading) {
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

    AnimatedVisibility(visible = sampleDetailsScreenUIState.error != null) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            item {
                Text(text = "${sampleDetailsScreenUIState.error}...")
            }
        }
    }

    AnimatedVisibility(visible = sampleDetailsScreenUIState.sampleHobby != null) {
        LazyColumn(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                CoilImage(
                    modifier =
                        Modifier.fillMaxWidth().fillMaxWidth(fraction = 0.6f)
                            .aspectRatio(ratio = 0.75f),
                    imageModel = { sampleDetailsScreenUIState.sampleHobby?.sampleHobbyThumbnail },
                    imageOptions =
                        ImageOptions(
                            contentScale = ContentScale.Crop,
                            contentDescription = "Sample Thumbnail...",
                        ),
                )
            }

            item {
                sampleDetailsScreenUIState.sampleHobby?.sampleHobbyDescription?.let {
                    SampleDetailRow(
                        sampleLabel = "Sample Hobby Description...",
                        sampleValue = it,
                    )
                }
            }
        }
    }
}

@Composable
fun SampleDetailRow(
    sampleLabel: String,
    sampleValue: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "$sampleLabel: ",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = sampleValue,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}