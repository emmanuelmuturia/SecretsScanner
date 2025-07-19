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
package emmanuelmuturia.sample.home.local.source.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import emmanuelmuturia.sample.home.source.local.database.SampleDatabase

/**
 * This is the Android-specific implementation of the project's [androidx.room.RoomDatabase]...
 */

fun getSampleDatabase(context: Context): SampleDatabase {
    val databaseFile = context.getDatabasePath("sample.db")
    return Room.databaseBuilder<SampleDatabase>(
        context = context.applicationContext,
        name = databaseFile.absolutePath,
    ).setDriver(driver = BundledSQLiteDriver())
        .build()
}