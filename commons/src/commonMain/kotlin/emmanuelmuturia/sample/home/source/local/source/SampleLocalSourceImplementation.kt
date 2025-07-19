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
package emmanuelmuturia.sample.home.source.local.source

import emmanuelmuturia.sample.commons.state.SampleResult
import emmanuelmuturia.sample.home.source.local.dao.SampleDao
import emmanuelmuturia.sample.home.source.local.entity.SampleEntity
import emmanuelmuturia.sample.home.source.remote.dto.SampleDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

/**
 * This is the Home feature's Local Data Source Implementation...
 */

class SampleLocalSourceImplementation(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val sampleDao: SampleDao,
    private val httpClient: HttpClient,
) : SampleLocalSource {
    override suspend fun getSamples(): Flow<List<SampleEntity>> {
        return withContext(context = coroutineDispatcher) {
            sampleDao.getSamples()
        }.onEach {
            if (it.isEmpty()) {
                fetchRemoteSamples()
            }
        }
    }

    override suspend fun fetchRemoteSamples() {
        withContext(context = coroutineDispatcher) {
            val response =
                httpClient.get(urlString = "") {
                    url {
                        parameters.append(name = "", value = "")
                    }
                }
            if (response.status == HttpStatusCode.OK) {
                sampleDao.insertSample(sampleEntity = response.body<SampleDTO>().toSampleEntity())
            } else {
                SampleResult.Error(error = response.status.description)
            }
        }
    }

    override suspend fun searchForSamples(sampleQuery: String) {
        withContext(context = coroutineDispatcher) {
            val response =
                httpClient.get(urlString = "") {
                    url {
                        parameters.append(name = "", value = sampleQuery)
                    }
                }
            if (response.status == HttpStatusCode.OK) {
                sampleDao.deleteAllSamples()
                sampleDao.insertSample(sampleEntity = response.body<SampleDTO>().toSampleEntity())
            } else {
                SampleResult.Error(error = response.status.description)
            }
        }
    }
}