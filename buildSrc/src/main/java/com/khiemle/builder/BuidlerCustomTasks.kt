package com.khiemle.builder

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property
import java.io.File

open class BuidlerCustomTasks: DefaultTask() {
    @Input
    val greeting: Property<String> = project.objects.property()

    @Internal
    val message: Provider<String> = greeting.map { it + "from Gradle" }

    @Input
    var destination: Any? = null
    private fun getDestination() : File {
        return project.file(destination!!)
    }

    @TaskAction
    fun greet() {
        val file = getDestination()
        file.parentFile.mkdirs()
        file.writeText("Hello from custom task!!!!")
    }

    @TaskAction
    fun printMessage() {
        logger.quiet(message.get())
    }
}