/*
 * Copyright 2025 Secrets Scanner
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
package emmanuelmuturia.secretsscanner.secretscanner.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import emmanuelmuturia.secretsscanner.secretscanner.source.local.entity.ScanResultEntity
import emmanuelmuturia.secretsscanner.secretscanner.ui.state.SecretsScannerHomeScreenUIState
import emmanuelmuturia.secretsscanner.secretscanner.ui.viewmodel.SecretsScannerHomeScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * This is the Secrets Scanner Screen...
 */

class SecretsScannerScreen() : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val secretsScannerHomeScreenViewModel = koinViewModel<SecretsScannerHomeScreenViewModel>()

        val secretsScannerHomeScreenUIState: SecretsScannerHomeScreenUIState by
            secretsScannerHomeScreenViewModel.secretsScannerUIState.collectAsStateWithLifecycle()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    modifier =
                        Modifier.semantics {
                            contentDescription = "Secret Scanner Home Screen Top App Bar..."
                        },
                    title = {
                        Text(
                            modifier =
                                Modifier.semantics {
                                    contentDescription =
                                        "Secret Scanner Home Screen Top App Bar Text..."
                                },
                            text = "Secret Scanner",
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
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        secretsScannerHomeScreenViewModel.scanFiles()
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = "Scan Files...",
                    )
                }
            },
        ) { paddingValues ->
            SecretsScannerScreenContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                secretsScannerHomeScreenUIState = secretsScannerHomeScreenUIState,
            )
        }
    }
}

@Composable
private fun SecretsScannerScreenContent(
    modifier: Modifier,
    secretsScannerHomeScreenUIState: SecretsScannerHomeScreenUIState,
) {
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

    AnimatedVisibility(visible = secretsScannerHomeScreenUIState.scanResults.isNotEmpty()) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(top = 70.dp, start = 14.dp, end = 14.dp),
        ) {
            items(items = secretsScannerHomeScreenUIState.scanResults) { result ->
                ScanResultItem(scanResult = result)
            }
        }
    }

    AnimatedVisibility(
        visible =
            !secretsScannerHomeScreenUIState.isLoading &&
                secretsScannerHomeScreenUIState.error == null &&
                secretsScannerHomeScreenUIState.scanResults.isEmpty(),
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "No scan results yet...\nTap the \"Refresh\" icon to begin...",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
fun ScanResultItem(scanResult: ScanResultEntity) {
    ElevatedCard(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "📄 ${scanResult.fileName}",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                text = "🔍 ${scanResult.matchType.label}",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )

            Text(
                text = "Line ${scanResult.lineNumber}: ${scanResult.lineContent}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                text = "Matched: ${scanResult.matchedValue}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}