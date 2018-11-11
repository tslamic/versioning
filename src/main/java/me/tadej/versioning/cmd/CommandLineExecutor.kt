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

import java.io.File
import java.io.OutputStream

interface CommandLineExecutor {
    /**
     * Executes the command and returns the OutputStream denoting the command result.
     */
    fun execute(
        command: String,
        workingDirectory: File = File("."),
        timeoutMillis: Long = 10_000,
        exitValue: Int = 0
    ): OutputStream
}
