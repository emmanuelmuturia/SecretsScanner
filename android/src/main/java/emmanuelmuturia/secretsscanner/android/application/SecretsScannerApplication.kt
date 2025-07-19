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
package emmanuelmuturia.secretsscanner.android.application

import android.app.Application
import emmanuelmuturia.secretsscanner.android.BuildConfig
import emmanuelmuturia.secretsscanner.commons.dependencyInjection.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import timber.log.Timber

/**
 * This is the Android-specific implementation of the project's entry point...
 */

class SecretsScannerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(tree = Timber.DebugTree())
        }
        initKoin {
            androidLogger()
            androidContext(androidContext = this@SecretsScannerApplication)
            modules(
                modules =
                    listOf(

                    ),
            )
        }
    }
}