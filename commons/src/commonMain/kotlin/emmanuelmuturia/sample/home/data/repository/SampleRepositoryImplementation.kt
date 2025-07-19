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
package emmanuelmuturia.sample.home.data.repository

import emmanuelmuturia.sample.home.data.model.Sample
import emmanuelmuturia.sample.home.data.model.toSampleHobby
import emmanuelmuturia.sample.home.source.local.source.SampleLocalSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * This is the Home feature's Repository Implementation...
 */

class SampleRepositoryImplementation(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val sampleLocalSource: SampleLocalSource,
) : SampleRepository {
    override suspend fun getSamples(): Flow<List<Sample>> {
        return withContext(context = coroutineDispatcher) {
            sampleLocalSource.getSamples().map { samples ->
                samples.map { sample ->
                    Sample(
                        sampleId = sample.sampleId,
                        sampleAge = sample.sampleAge,
                        sampleName = sample.sampleName,
                        sampleHobbies = sample.sampleHobbies?.map { it.toSampleHobby() },
                    )
                }
            }
        }
    }

    override suspend fun searchForSample(sampleQuery: String) {
        return withContext(context = coroutineDispatcher) {
            sampleLocalSource.searchForSamples(sampleQuery = sampleQuery)
        }
    }
}