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
package emmanuelmuturia.secretsscanner.home.source.local.source

import emmanuelmuturia.secretsscanner.home.source.local.entity.ProjectFileEntity
import emmanuelmuturia.secretsscanner.home.source.local.entity.ScanResultEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

/**
 * This is the Home feature's Local Data Source Implementation...
 */

class SecretsScannerLocalSourceImplementation(
    private val coroutineDispatcher: CoroutineDispatcher,
) : SecretsScannerLocalSource {
    override suspend fun scanForSecrets(
        files: List<ProjectFileEntity>
    ): Flow<List<ScanResultEntity>> {
        return withContext(context = coroutineDispatcher) {
            val results = mutableListOf<ScanResultEntity>()

            for (file in files) {
                for (pattern in secretPatterns) {
                    val matches = pattern.findAll(input = file.content)
                    for (match in matches) {
                        results.add(
                            element = ScanResultEntity(
                                fileName = file.fileName,
                                match = match.value
                            )
                        )
                    }
                }
            }

            flowOf(value = results)
        }
    }
}

private val secretPatterns = listOf(
    Regex(pattern = "AKIA[0-9A-Z]{16}"),          // AWS Key...
    Regex(pattern = "AIza[0-9A-Za-z-_]{35}"),     // Google API...
    Regex(pattern = "sk_live_[0-9a-zA-Z]{24}"),   // Stripe key...
    Regex(pattern = "(?i)password\\s*=\\s*.+"),   // Password...
    Regex(pattern = "(?i)api[_-]?key\\s*=\\s*.+") // API Key...
)