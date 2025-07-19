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
package emmanuelmuturia.secretsscanner.home.source.local.dependencyInjection

import emmanuelmuturia.secretsscanner.home.source.local.source.SecretsScannerLocalSource
import emmanuelmuturia.secretsscanner.home.source.local.source.SecretsScannerLocalSourceImplementation
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * This is the project's Data Source [:local] Koin Module...
 */

val secretsScannerLocalSourceKoinModule =
    module {
        singleOf(::SecretsScannerLocalSourceImplementation).bind(clazz = SecretsScannerLocalSource::class)
    }