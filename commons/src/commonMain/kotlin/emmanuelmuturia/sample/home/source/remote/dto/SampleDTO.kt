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
package emmanuelmuturia.sample.home.source.remote.dto

import emmanuelmuturia.sample.home.source.local.entity.SampleEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SampleDTO(
    @SerialName(value = "sampleId")
    val sampleId: String,
    @SerialName(value = "sampleAge")
    val sampleAge: Int? = null,
    @SerialName(value = "sampleName")
    val sampleName: String? = null,
    @SerialName(value = "sampleHobbies")
    val sampleHobbies: List<SampleHobbyDTO>? = null,
) {
    fun toSampleEntity(): SampleEntity {
        return SampleEntity(
            sampleId = sampleId,
            sampleAge = sampleAge,
            sampleName = sampleName,
            sampleHobbies = sampleHobbies?.map { it.toSampleHobbyEntity() },
        )
    }
}