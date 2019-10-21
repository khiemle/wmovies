package com.khiemle.builder

import org.gradle.api.Plugin
import org.gradle.api.Project

open class BuilderPlugin  : Plugin<Project> {
    override fun apply(target: Project) {
        println("Hello World from Plugin")
    }

}