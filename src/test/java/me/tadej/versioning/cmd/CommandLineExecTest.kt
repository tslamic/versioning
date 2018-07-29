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

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isNotNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.apache.commons.exec.DefaultExecutor
import org.junit.Test
import org.mockito.Mockito
import java.io.File
import java.io.OutputStream

class CommandLineExecTest {
    @Test
    fun execute_invokesExecuteOnExecutor() {
        val converter: CommandLineConverter<String> = mock()
        val factory = Factory()
        val exec = CommandLineExec(converter, factory)

        exec.execute("command")

        verify(factory.mock).execute(isNotNull(), isNotNull<Map<String, String>>())
        verify(converter).convert(any())
    }

    private class Factory : CommandLineExec.DefaultExecutorFactory {
        val mock = Mockito.mock(DefaultExecutor::class.java)!!

        override fun create(
            workingDirectory: File,
            outputStream: OutputStream,
            timeoutMillis: Long,
            exitValue: Int
        ): DefaultExecutor = mock
    }
}
