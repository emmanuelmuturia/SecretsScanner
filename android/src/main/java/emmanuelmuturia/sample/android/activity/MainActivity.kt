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
package emmanuelmuturia.sample.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cafe.adriel.voyager.navigator.Navigator
import emmanuelmuturia.sample.android.theme.SampleTheme
import emmanuelmuturia.sample.home.ui.screens.SampleHomeScreen
import emmanuelmuturia.sample.home.ui.viewmodel.SampleHomeScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * This the main [android.app.Activity] of the project...
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val sampleHomeScreenViewModel: SampleHomeScreenViewModel by viewModel()
        installSplashScreen().setKeepOnScreenCondition {
            sampleHomeScreenViewModel.sampleHomeScreenUIState.value.isLoading
        }
        super.onCreate(savedInstanceState)
        setContent {
            SampleTheme {
                Surface(
                    modifier = Modifier.Companion.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Navigator(
                        screen = SampleHomeScreen(),
                    )
                }
            }
        }
    }
}