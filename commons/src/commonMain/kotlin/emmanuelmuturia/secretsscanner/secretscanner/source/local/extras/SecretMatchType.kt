/*
 * Copyright 2025 Secrets Scanner
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
package emmanuelmuturia.secretsscanner.secretscanner.source.local.extras

enum class SecretMatchType(val label: String) {
    AWS(label = "AWS Access Key"),
    GOOGLE(label = "Google API Key"),
    STRIPE(label = "Stripe Key"),
    PASSWORD(label = "Password"),
    API_KEY(label = "API Key"),
    UNKNOWN(label = "Unknown"),
}