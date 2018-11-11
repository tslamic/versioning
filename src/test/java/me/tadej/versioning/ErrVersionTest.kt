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

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ErrVersionTest {
    @Test
    fun versionName_throwsException_returnsDefault() {
        val errorName = "defaultVersionName"
        val version = ErrVersion(ThrowableVersion, errorName = errorName)
        val name = version.versionName()
        assertThat(name).isEqualTo(errorName)
    }

    @Test
    fun versionCode_throwsException_returnsDefault() {
        val errorCode = 123
        val version = ErrVersion(ThrowableVersion, errorCode = errorCode)
        val code = version.versionCode()
        assertThat(code).isEqualTo(errorCode)
    }

    @Test
    fun versionName_noException_returnsName() {
        val versionName = "versionName"
        val version = ErrVersion(SimpleVersion(versionName, 0))
        val name = version.versionName()
        assertThat(name).isEqualTo(versionName)
    }

    @Test
    fun versionCode_noException_returnsCode() {
        val versionCode = 123
        val version = ErrVersion(SimpleVersion("", versionCode))
        val code = version.versionCode()
        assertThat(code).isEqualTo(versionCode)
    }

    private class SimpleVersion(
        val versionName: String,
        val versionCode: Int
    ) : Version {
        override fun versionName(): String = versionName
        override fun versionCode(): Int = versionCode
    }

    private object ThrowableVersion : Version {
        override fun versionName(): String {
            throw Exception()
        }

        override fun versionCode(): Int {
            throw Exception()
        }
    }
}
