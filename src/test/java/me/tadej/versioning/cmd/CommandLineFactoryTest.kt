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
package me.tadej.versioning.cmd

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.File

class CommandLineFactoryTest {
    @Test
    fun create_withDefaultFactory_createsDefaultExecutor() {
        val dir: File = mock()
        val stream = ByteArrayOutputStream()

        val factory = CommandLineExec.Factory
        val executor = factory.create(dir, stream, 10_000, 0)

        assertThat(executor.workingDirectory).isEqualTo(dir)
        assertThat(executor.streamHandler).isNotNull()
        assertThat(executor.watchdog).isNotNull()
    }

    @Test
    fun create_withDefaultFactory_createsOutputStream() {
        val factory = CommandLineExec.Factory
        val stream = factory.create()
        assertThat(stream).isNotNull()
        assertThat(stream).isInstanceOf(ByteArrayOutputStream::class.java)
    }
}
