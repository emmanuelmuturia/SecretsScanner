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
import emmanuelmuturia.secretsscanner.home.data.repository.SecretsScannerRepository
import emmanuelmuturia.secretsscanner.home.ui.state.SecretsScannerDetailsScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * This is the Home feature's Details Screen [ViewModel]...
 */

class SecretsScannerDetailsScreenViewModel(
    private val secretsScannerRepository: SecretsScannerRepository,
) : ViewModel() {
    val secretsScannerDetailsScreenUIState = MutableStateFlow(value = SecretsScannerDetailsScreenUIState())

    fun getSampleById(sampleId: String) {
        secretsScannerDetailsScreenUIState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            secretsScannerRepository.getSamples().collect { samples ->

                val sampleHobby = samples.flatMap { it.sampleHobbies ?: emptyList() }.firstOrNull { it.sampleHobbyId == sampleId }

                if (sampleHobby != null) {
                    secretsScannerDetailsScreenUIState.update {
                        it.copy(
                            sampleHobby = sampleHobby,
                            isLoading = false,
                        )
                    }
                } else {
                    secretsScannerDetailsScreenUIState.update {
                        it.copy(
                            error = "Sample Hobby not found...",
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }
}