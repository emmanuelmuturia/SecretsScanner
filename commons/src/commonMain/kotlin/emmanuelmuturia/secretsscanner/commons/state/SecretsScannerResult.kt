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
package emmanuelmuturia.secretsscanner.commons.state

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * This wrapper represents the project's State: [Success] or [Error]...
 */

sealed class SecretsScannerResult<out T> {
    data class Success<out T>(val data: T) : SecretsScannerResult<T>()

    data class Error(val error: String) : SecretsScannerResult<Nothing>()
}

/**
 * This is the project's State Management's extension function that returns the State as a [Flow]...
 */

fun <T> Flow<T>.asResult(): Flow<SecretsScannerResult<T>> {
    return this.map<T, SecretsScannerResult<T>> {
        SecretsScannerResult.Success(data = it)
    }.catch {
        emit(value = SecretsScannerResult.Error(error = it.message.toString()))
    }
}