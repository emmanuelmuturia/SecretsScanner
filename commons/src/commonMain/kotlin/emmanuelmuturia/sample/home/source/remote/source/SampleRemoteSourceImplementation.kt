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
package emmanuelmuturia.sample.home.source.remote.source

import emmanuelmuturia.sample.commons.state.SampleResult
import emmanuelmuturia.sample.home.source.remote.dto.SampleDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * This is the Home feature's Remote Data Source Implementation...
 */

class SampleRemoteSourceImplementation(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val httpClient: HttpClient,
) : SampleRemoteSource {
    override suspend fun getSamples(sampleQuery: String): SampleResult<List<SampleDTO>> {
        return withContext(context = coroutineDispatcher) {
            try {
                val response =
                    httpClient.get(urlString = "") {
                        url {
                            parameters.append(name = "", value = sampleQuery)
                        }
                    }
                if (response.status == HttpStatusCode.OK) {
                    SampleResult.Success(data = response.body())
                } else {
                    SampleResult.Error(error = response.status.description)
                }
            } catch (e: Exception) {
                SampleResult.Error(error = e.message ?: "Unknown Error...")
            }
        }
    }
}