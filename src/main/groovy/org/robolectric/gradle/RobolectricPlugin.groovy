package org.robolectric.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test

/**
 * Robolectric gradle plugin.
 */
class RobolectricPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        // Configure the project
        def configuration = new Configuration(project)

        // Configure the test tasks
        configuration.variants.all { variant ->
            def taskName = "test${variant.name.capitalize()}"
            def assets = variant.mergeAssets.outputDir
            def manifest = variant.outputs.first().processManifest.manifestOutputFile
            def resources = variant.mergeResources.outputDir
            def packageName = project.android.defaultConfig.applicationId

            // Set RobolectricTestRunner properties
            def task = project.tasks.findByName(taskName) as Test
            task.systemProperty "android.assets", assets
            task.systemProperty "android.manifest", manifest
            task.systemProperty "android.resources", resources
            task.systemProperty "android.package", packageName

            project.logger.info("Configuring task: ${taskName}")
            project.logger.info("Robolectric assets: ${assets}")
            project.logger.info("Robolectric manifest: ${manifest}")
            project.logger.info("Robolectric resources: ${resources}")
            project.logger.info("Robolectric package: ${packageName}")
        }
    }
}
