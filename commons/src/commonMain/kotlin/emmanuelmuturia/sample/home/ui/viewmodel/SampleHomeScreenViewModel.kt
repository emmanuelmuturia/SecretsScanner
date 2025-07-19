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
import emmanuelmuturia.sample.commons.state.SampleResult
import emmanuelmuturia.sample.commons.state.asResult
import emmanuelmuturia.sample.home.data.repository.SampleRepository
import emmanuelmuturia.sample.home.ui.state.SampleHomeScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * This is the Home feature's Home Screen [ViewModel]...
 */

class SampleHomeScreenViewModel(
    private val sampleRepository: SampleRepository,
) : ViewModel() {
    val sampleHomeScreenUIState = MutableStateFlow(value = SampleHomeScreenUIState())

    init {
        getSamples()
    }

    fun getSamples() {
        sampleHomeScreenUIState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            sampleRepository.getSamples().asResult().collect { result ->

                when (result) {
                    is SampleResult.Success -> {
                        sampleHomeScreenUIState.update {
                            it.copy(
                                samples = result.data,
                                isLoading = false,
                            )
                        }
                    }

                    is SampleResult.Error -> {
                        sampleHomeScreenUIState.update {
                            it.copy(
                                error = result.error,
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    fun searchBooks(sampleQuery: String) {
        viewModelScope.launch {
            sampleHomeScreenUIState.update { it.copy(isLoading = true) }

            val result =
                runCatching {
                    sampleRepository.searchForSample(sampleQuery = sampleQuery)
                }

            result.onSuccess {
                getSamples()
            }.onFailure { throwable ->
                sampleHomeScreenUIState.update {
                    it.copy(error = throwable.message ?: "Unknown Error...", isLoading = false)
                }
            }
        }
    }
}