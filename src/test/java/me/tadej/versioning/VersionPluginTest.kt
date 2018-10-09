package me.tadej.versioning

import com.nhaarman.mockitokotlin2.*
import org.gradle.api.Action
import org.gradle.api.Task
import org.gradle.api.internal.plugins.ExtensionContainerInternal
import org.gradle.api.internal.project.DefaultProject
import org.junit.Test
import java.io.File

class VersionPluginTest {

    @Test
    fun verify_PluginTaskHit() {
        val mockedProject = mock<DefaultProject>()
        val mockedExtension = mock<VersionExtension>()
        val mockedContainer = mock<ExtensionContainerInternal>()
        val mockedTask = mock<Task>()

        whenever(mockedProject.extensions).then { mockedContainer }
        whenever(mockedContainer.create(VersionPlugin.EXTENSION, VersionExtension::class.java)).then { mockedExtension }
        whenever(mockedProject.projectDir).then { mock<File>() }

        whenever(mockedProject.task("printVersionName")).then { mockedTask }
        whenever(mockedProject.task("printVersionCode")).then { mockedTask }
        whenever(mockedTask.doLast(any<Action<Task>>())).then { mockedTask }

        VersionPlugin().apply(mockedProject)

        verify(mockedProject).extensions
        verify(mockedContainer).create(VersionPlugin.EXTENSION, VersionExtension::class.java)
        verify(mockedProject).projectDir

        verify(mockedProject).task("printVersionName")
        verify(mockedTask, atMost(2)).group = VersionPlugin.EXTENSION
        verify(mockedTask, atMost(2)).description = VersionPlugin.DESCRIPTION_NAME

        verify(mockedProject).task("printVersionCode")
        verify(mockedTask, atMost(2)).group = VersionPlugin.EXTENSION
        verify(mockedTask, atMost(2)).description = VersionPlugin.DESCRIPTION_CODE
    }

}
