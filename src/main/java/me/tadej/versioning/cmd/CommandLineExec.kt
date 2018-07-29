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

internal class CommandLineExec<out T>(
    private val converter: CommandLineConverter<T>,
    private val factory: DefaultExecutorFactory = Factory
) : CommandLineExecutor<T> {

    @Throws(ExecuteException::class)
    override fun execute(
        command: String,
        workingDirectory: File,
        timeoutMillis: Long,
        exitValue: Int
    ): T {
        val outputStream = ByteArrayOutputStream(128)
        val executor = factory.create(
            workingDirectory,
            outputStream,
            timeoutMillis,
            exitValue
        )

        val parsed = CommandLine.parse(command)
        val environment = EnvironmentUtils.getProcEnvironment()

        // Execute the command. If the exit status differs from
        // the specified exitValue, an `ExecuteException` will be thrown.
        executor.execute(parsed, environment)

        return converter.convert(outputStream)
    }

    interface DefaultExecutorFactory {
        fun create(
            workingDirectory: File,
            outputStream: OutputStream,
            timeoutMillis: Long,
            exitValue: Int
        ): DefaultExecutor
    }

    private object Factory : DefaultExecutorFactory {
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
    }
}
