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
package emmanuelmuturia.secretsscanner.secretscanner.ui.dependencyInjection

import emmanuelmuturia.secretsscanner.secretscanner.data.dependencyInjection.secretsScannerDataKoinModule
import emmanuelmuturia.secretsscanner.secretscanner.ui.viewmodel.SecretsScannerHomeScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * This is the project's UI Layer Koin Module...
 */

val secretsScannerUIKoinModule =
    module {

        viewModelOf(::SecretsScannerHomeScreenViewModel)

        includes(
            module =
                listOf(
                    secretsScannerDataKoinModule,
                ),
        )
    }