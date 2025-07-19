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
package emmanuelmuturia.sample.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import emmanuelmuturia.sample.home.data.repository.SampleRepository
import emmanuelmuturia.sample.home.ui.state.SampleDetailsScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * This is the Home feature's Details Screen [ViewModel]...
 */

class SampleDetailsScreenViewModel(
    private val sampleRepository: SampleRepository,
) : ViewModel() {
    val sampleDetailsScreenUIState = MutableStateFlow(value = SampleDetailsScreenUIState())

    fun getSampleById(sampleId: String) {
        sampleDetailsScreenUIState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            sampleRepository.getSamples().collect { samples ->

                val sampleHobby = samples.flatMap { it.sampleHobbies ?: emptyList() }.firstOrNull { it.sampleHobbyId == sampleId }

                if (sampleHobby != null) {
                    sampleDetailsScreenUIState.update {
                        it.copy(
                            sampleHobby = sampleHobby,
                            isLoading = false,
                        )
                    }
                } else {
                    sampleDetailsScreenUIState.update {
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