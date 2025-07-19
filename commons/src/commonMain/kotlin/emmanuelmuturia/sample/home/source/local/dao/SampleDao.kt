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
package emmanuelmuturia.sample.home.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import emmanuelmuturia.sample.home.source.local.entity.SampleEntity
import kotlinx.coroutines.flow.Flow

/**
 * This is the Home feature's [Dao]...
 */

@Dao
interface SampleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSample(sampleEntity: SampleEntity)

    @Transaction
    @Query(value = "SELECT * FROM SampleEntity")
    fun getSamples(): Flow<List<SampleEntity>>

    @Transaction
    @Query(value = "DELETE FROM SampleEntity")
    suspend fun deleteAllSamples()
}