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

import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecutor
import org.apache.commons.exec.ExecuteException
import org.apache.commons.exec.ExecuteWatchdog
import org.apache.commons.exec.PumpStreamHandler
import org.apache.commons.exec.environment.EnvironmentUtils
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStream

internal open class CommandLineExec(
    private val executorFactory: DefaultExecutorFactory = Factory,
    private val streamFactory: OutputStreamFactory = Factory
) : CommandLineExecutor {

    @Throws(ExecuteException::class)
    final override fun execute(
        command: String,
        workingDirectory: File,
        timeoutMillis: Long,
        exitValue: Int
    ): OutputStream {
        val outputStream = streamFactory.create()
        val executor = executorFactory.create(
            workingDirectory,
            outputStream,
            timeoutMillis,
            exitValue
        )
        val parsed = CommandLine.parse(command)
        val environment = EnvironmentUtils.getProcEnvironment()
        executor.execute(parsed, environment)
        return outputStream
    }

    interface DefaultExecutorFactory {
        fun create(
            workingDirectory: File,
            outputStream: OutputStream,
            timeoutMillis: Long,
            exitValue: Int
        ): DefaultExecutor
    }

    interface OutputStreamFactory {
        fun create(): OutputStream
    }

    internal object Factory : DefaultExecutorFactory, OutputStreamFactory {
        override fun create(
            workingDirectory: File,
            outputStream: OutputStream,
            timeoutMillis: Long,
            exitValue: Int
        ): DefaultExecutor {
            val executor = DefaultExecutor()
            executor.workingDirectory = workingDirectory
            executor.streamHandler = PumpStreamHandler(outputStream)
            executor.watchdog = ExecuteWatchdog(timeoutMillis)
            executor.setExitValue(exitValue)
            return executor
        }

        override fun create(): OutputStream = ByteArrayOutputStream(128)
    }
}
