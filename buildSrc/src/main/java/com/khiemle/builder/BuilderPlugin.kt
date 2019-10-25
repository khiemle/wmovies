package com.khiemle.builder

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.register

open class BuilderPlugin  : Plugin<Project> {

    val listApps = mutableListOf<ApplicationVariantConfig>()
    override fun apply(project: Project) {

        val moviesExtension = project.extensions.create<MoviesExtension>("moviesExtension")
        moviesExtension.applicationId?.let {
            println("Finished configure from app with ${moviesExtension.applicationId} and ${moviesExtension.name}")
        }

        project.extra["greetingFile"] = "${project.buildDir}/createdByPlugin.txt"

        project.tasks.register<BuidlerCustomTasks>("greet") {
            greeting.set("Good morning!!!")
            destination = { project.extra["greetingFile"] }
        }

        createListApps(project)

        project.task("installAllApps") {
            for (i in 1..20) {
                dependsOn("installApp${i}Debug")
            }
        }
        project.task("uninstallAllApps") {
            dependsOn("uninstallAll")
        }
    }


    private fun createListApps(project: Project) {
        for (i in 1..20) {
            listApps.add(ApplicationVariantConfig("com.khiemle.app$i", "app$i"))
        }
    }
}