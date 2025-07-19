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
package emmanuelmuturia.sample.home.source.local.typeConverter

import androidx.room.TypeConverter
import emmanuelmuturia.sample.home.source.local.entity.SampleHobbyEntity
import kotlinx.serialization.json.Json

/**
 * This is the Home feature's [TypeConverter] for the Local Data Source...
 */

class SampleTypeConverters {
    private val json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = true
        }

    @TypeConverter
    fun fromSampleHobbies(sampleHobbies: List<SampleHobbyEntity>): String {
        return json.encodeToString(value = sampleHobbies)
    }

    @TypeConverter
    fun toSampleHobbies(sampleHobbies: String): List<SampleHobbyEntity> {
        return json.decodeFromString(string = sampleHobbies)
    }
}