/*
 * Copyright 2018 Tadej Slamic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.tadej.versioning

internal class ErrVersion(
    private val version: Version,
    private val errorName: String = "error",
    private val errorCode: Int = 1
) : Version {

    override fun versionName(): String = try {
        version.versionName()
    } catch (ex: Exception) {
        log(ex)
        errorName
    }

    override fun versionCode(): Int = try {
        version.versionCode()
    } catch (ex: Exception) {
        log(ex)
        errorCode
    }

    private fun log(ex: Exception) {
        System.err.printf(
            "versioning plugin error: '%s' - have you done your first commit?\n", ex
        )
    }
}
