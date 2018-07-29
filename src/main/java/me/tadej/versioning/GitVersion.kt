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

import me.tadej.versioning.cmd.CommandLineExec
import me.tadej.versioning.cmd.CommandLineExecutor
import me.tadej.versioning.cmd.StringConverter
import java.io.File

internal class GitVersion(
    private val workingDirectory: File,
    private val executor: CommandLineExecutor<String> = CommandLineExec(StringConverter())
) : Version {

    override fun versionName(): String = executor.execute(GIT_DESCRIBE, workingDirectory)
    override fun versionCode(): Int = executor.execute(GIT_COMMIT_COUNT, workingDirectory).toInt()

    companion object {
        internal const val GIT_DESCRIBE = "git describe --long --tags --dirty --always"
        internal const val GIT_COMMIT_COUNT = "git rev-list --count HEAD"
    }
}
