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

import emmanuelmuturia.sample.home.source.local.entity.SampleHobbyEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SampleHobbyDTO(
    @SerialName(value = "hobbyId")
    val sampleHobbyId: String? = null,
    @SerialName(value = "hobbyName")
    val sampleHobbyName: String? = null,
    @SerialName(value = "hobbyThumbnail")
    val sampleHobbyThumbnail: String? = null,
    @SerialName(value = "hobbyDescription")
    val sampleHobbyDescription: String? = null,
    @SerialName(value = "hobbyBenefits")
    val sampleHobbyBenefits: List<SampleHobbyBenefitDTO>? = null,
) {
    fun toSampleHobbyEntity(): SampleHobbyEntity {
        return SampleHobbyEntity(
            sampleHobbyId = sampleHobbyId,
            sampleHobbyName = sampleHobbyName,
            sampleHobbyThumbnail = sampleHobbyThumbnail,
            sampleHobbyDescription = sampleHobbyDescription,
            sampleHobbyBenefits = sampleHobbyBenefits?.map { it.toSampleHobbyBenefitEntity() },
        )
    }
}