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
import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecutor
import org.junit.Test
import org.mockito.Mockito
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStream

class CommandLineExecTest {
    @Test
    fun execute_invokesExecuteOnExecutor() {
        val command = "ls -l"
        val dir: File = mock()
        val timeout = 10_000L
        val exitValue = 0

        val stream = ByteArrayOutputStream()
        val holder = Holder.create(stream)
        holder.exec.execute(command, dir, timeout, exitValue)

        val cmd = holder.commandLineCaptor.firstValue
        assertThat(cmd.executable).isEqualTo("ls")
        assertThat(cmd.arguments).asList().contains("-l")

        val env = holder.environmentCaptor.firstValue
        assertThat(env).isNotEmpty()
    }

    private data class Holder(
        val exec: CommandLineExec,
        val commandLineCaptor: KArgumentCaptor<CommandLine>,
        val environmentCaptor: KArgumentCaptor<Map<String, String>>
    ) {
        companion object {
            fun create(output: OutputStream): Holder {
                val mock = Mockito.mock(DefaultExecutor::class.java)!!
                val cmd = argumentCaptor<CommandLine>()
                val env = argumentCaptor<Map<String, String>>()
                whenever(mock.execute(cmd.capture(), env.capture())).thenReturn(0)
                val factory = Factory(mock, output)
                val exec = CommandLineExec(factory, factory)
                return Holder(exec, cmd, env)
            }
        }
    }

    private class Factory(
        val mock: DefaultExecutor,
        val stream: OutputStream
    ) : CommandLineExec.DefaultExecutorFactory, CommandLineExec.OutputStreamFactory {

        override fun create(
            workingDirectory: File,
            outputStream: OutputStream,
            timeoutMillis: Long,
            exitValue: Int
        ): DefaultExecutor = mock

        override fun create(): OutputStream = stream
    }
}
