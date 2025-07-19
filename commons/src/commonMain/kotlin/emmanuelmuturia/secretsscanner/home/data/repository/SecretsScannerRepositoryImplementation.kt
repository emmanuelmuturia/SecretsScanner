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
package emmanuelmuturia.secretsscanner.home.data.repository

import emmanuelmuturia.secretsscanner.home.source.local.entity.ProjectFileEntity
import emmanuelmuturia.secretsscanner.home.source.local.source.SecretsScannerLocalSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * This is the Home feature's Repository Implementation...
 */

class SecretsScannerRepositoryImplementation(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val secretsScannerLocalSource: SecretsScannerLocalSource,
) : SecretsScannerRepository {
    override suspend fun scanForSecrets() {
        withContext(context = coroutineDispatcher) {
            secretsScannerLocalSource.scanForSecrets(
                files = fakeProjectFiles
            )
        }
    }
}

private val fakeProjectFiles = listOf(
    ProjectFileEntity(fileName = "config.properties", content = "api_key=AIzaSyXXXX"),
    ProjectFileEntity(fileName = "MainActivity.kt", content = "val password = \"123456\""),
    ProjectFileEntity(fileName = "build.gradle", content = "// no secrets here"),
    ProjectFileEntity(
        fileName = "secrets.env",
        content = "AWS_SECRET_ACCESS_KEY=AKIAIOSFODNN7EXAMPLE"
    )
)