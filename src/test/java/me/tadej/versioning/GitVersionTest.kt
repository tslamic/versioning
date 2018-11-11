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
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import me.tadej.versioning.cmd.CommandLineExecutor
import org.junit.Test
import java.io.File

class GitVersionTest {
    private val executor: CommandLineExecutor = mock()
    private val workingDirectory: File = mock()

    @Test
    fun versionName_executesCommand() {
        val version = GitVersion(workingDirectory, executor)
        val versionName = "versionName"
        val stream = versionName.asOutputStream()
        whenever(executor.execute(any(), any(), any(), any())).thenReturn(stream)

        val name = version.versionName()

        assertThat(name).isEqualTo(versionName)
        verify(executor).execute(
            eq(GitVersion.GIT_DESCRIBE),
            eq(workingDirectory),
            eq(10_000),
            eq(0)
        )
    }

    @Test
    fun versionCode_executesCommand() {
        val version = GitVersion(workingDirectory, executor)
        val versionCode = 123
        val stream = versionCode.toString().asOutputStream()
        whenever(executor.execute(any(), any(), any(), any())).thenReturn(stream)

        val code = version.versionCode()

        assertThat(code).isEqualTo(versionCode)
        verify(executor).execute(
            eq(GitVersion.GIT_COMMIT_COUNT),
            eq(workingDirectory),
            eq(10_000),
            eq(0)
        )
    }
}
