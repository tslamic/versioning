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
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import groovy.lang.Closure
import org.junit.Test

class VersionExtensionTest {
    private val version: Version = mock()

    @Test
    fun versionCode_noDecorator_returnsUndecoratedVersionCode() {
        val versionCode = 123
        whenever(version.versionCode()).thenReturn(versionCode)

        val extension = VersionExtension()
        extension.version = version

        val code = extension.versionCode()

        assertThat(code).isEqualTo(versionCode)
    }

    @Test
    fun versionCode_withDecorator_returnsDecoratedVersionCode() {
        val versionCode = 123
        whenever(version.versionCode()).thenReturn(versionCode)

        val extension = VersionExtension()
        extension.version = version

        val decoratedCode = 456
        val decorator: Closure<Int> = mock()
        whenever(decorator.call(any())).thenReturn(decoratedCode)
        extension.decorateVersionCode = decorator

        assertThat(version.versionCode()).isEqualTo(versionCode)
        assertThat(extension.versionCode()).isEqualTo(decoratedCode)
    }

    @Test
    fun versionName_noDecorator_returnsUndecoratedVersionName() {
        val versionName = "versionName"
        whenever(version.versionName()).thenReturn(versionName)

        val extension = VersionExtension()
        extension.version = version
        val name = extension.versionName()

        assertThat(name).isEqualTo(versionName)
    }

    @Test
    fun versionName_withDecorator_returnsDecoratedVersionName() {
        val versionName = "versionName"
        whenever(version.versionName()).thenReturn(versionName)

        val extension = VersionExtension()
        extension.version = version

        val decoratedName = "decoratedVersionName"
        val decorator: Closure<String> = mock()
        whenever(decorator.call(any())).thenReturn(decoratedName)
        extension.decorateVersionName = decorator

        assertThat(version.versionName()).isEqualTo(versionName)
        assertThat(extension.versionName()).isEqualTo(decoratedName)
    }
}
