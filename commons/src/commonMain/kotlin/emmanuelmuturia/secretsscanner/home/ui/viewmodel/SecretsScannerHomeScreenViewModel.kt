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
package emmanuelmuturia.secretsscanner.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import emmanuelmuturia.secretsscanner.commons.state.SecretsScannerResult
import emmanuelmuturia.secretsscanner.commons.state.asResult
import emmanuelmuturia.secretsscanner.home.data.repository.SecretsScannerRepository
import emmanuelmuturia.secretsscanner.home.source.local.entity.ScanResultEntity
import emmanuelmuturia.secretsscanner.home.ui.state.SecretsScannerHomeScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * This is the Home feature's Home Screen [ViewModel]...
 */

class SecretsScannerHomeScreenViewModel(
    private val secretsScannerRepository: SecretsScannerRepository,
) : ViewModel() {

    val secretsScannerUIState = MutableStateFlow(value = SecretsScannerHomeScreenUIState())

    fun scanFiles() {
        viewModelScope.launch {
            secretsScannerUIState.update { it.copy(isLoading = true) }
            secretsScannerRepository.scanForSecrets().asResult().collect { result ->
                when(result) {

                    is SecretsScannerResult.Success -> {
                        secretsScannerUIState.update {
                            it.copy(
                                scanResults = result.data,
                                isLoading = false
                            )
                        }
                    }

                    is SecretsScannerResult.Error -> {
                        secretsScannerUIState.update {
                            it.copy(
                                error = result.error,
                                isLoading = false
                            )
                        }
                    }

                }
            }
        }
    }

}