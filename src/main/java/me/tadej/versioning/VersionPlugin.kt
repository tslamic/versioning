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

import org.gradle.api.Plugin
import org.gradle.api.Project

class VersionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(EXTENSION, VersionExtension::class.java)
        val version = GitVersion(project.projectDir)
        extension.version = ErrVersion(version)

        val name = project.task("printVersionName").doLast {
            println(extension.version.versionName())
        }
        name.group = EXTENSION
        name.description = DESCRIPTION_NAME

        val code = project.task("printVersionCode").doLast {
            println(extension.version.versionCode())
        }
        code.group = EXTENSION
        code.description = DESCRIPTION_CODE
    }

    companion object {
        const val EXTENSION = "versioning"
        const val DESCRIPTION_NAME = "Prints the version name to the standard output."
        const val DESCRIPTION_CODE = "Prints the version code to the standard output."
    }
}
