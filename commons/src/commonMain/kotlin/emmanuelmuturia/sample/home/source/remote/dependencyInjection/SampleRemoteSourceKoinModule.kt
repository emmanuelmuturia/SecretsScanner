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
package emmanuelmuturia.sample.home.source.remote.dependencyInjection

import emmanuelmuturia.sample.home.source.remote.source.SampleRemoteSource
import emmanuelmuturia.sample.home.source.remote.source.SampleRemoteSourceImplementation
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * This is the project's Data Source [:remote] Koin Module...
 */

val sampleRemoteSourceKoinModule =
    module {
        single {
            HttpClient {
                install(plugin = ContentNegotiation) {
                    json(
                        json =
                            Json {
                                prettyPrint = true
                                isLenient = true
                                ignoreUnknownKeys = true
                            },
                    )
                }
            }
        }

        singleOf(::SampleRemoteSourceImplementation).bind(clazz = SampleRemoteSource::class)
    }