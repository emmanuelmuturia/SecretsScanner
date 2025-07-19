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
package emmanuelmuturia.secretsscanner.secretscanner.source.local.source

import emmanuelmuturia.secretsscanner.secretscanner.source.local.entity.ProjectFileEntity
import emmanuelmuturia.secretsscanner.secretscanner.source.local.entity.ScanResultEntity
import emmanuelmuturia.secretsscanner.secretscanner.source.local.extras.SecretMatchType
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
    override suspend fun scanForSecrets(files: List<ProjectFileEntity>): Flow<List<ScanResultEntity>> =
        withContext(context = coroutineDispatcher) {
            val results = mutableListOf<ScanResultEntity>()

            for (file in files) {
                val lines = file.content.lines()
                for ((lineNumber, line) in lines.withIndex()) {
                    for ((regex, matchType) in secretPatternsWithType) {
                        val matches = regex.findAll(input = line)
                        for (match in matches) {
                            results.add(
                                element =
                                    ScanResultEntity(
                                        fileName = file.fileName,
                                        matchedValue = match.value,
                                        matchType = matchType,
                                        lineNumber = lineNumber + 1,
                                        lineContent = line.trim(),
                                    ),
                            )
                        }
                    }
                }
            }

            flowOf(value = results)
        }
}

private val secretPatternsWithType =
    listOf(
        Regex(pattern = "AKIA[0-9A-Z]{16}") to SecretMatchType.AWS,
        Regex(pattern = "AIza[0-9A-Za-z-_]{35}") to SecretMatchType.GOOGLE,
        Regex(pattern = "sk_live_[0-9a-zA-Z]{24}") to SecretMatchType.STRIPE,
        Regex(pattern = "(?i)password\\s*=\\s*.+") to SecretMatchType.PASSWORD,
        Regex(pattern = "(?i)api[_-]?key\\s*=\\s*.+") to SecretMatchType.API_KEY,
    )