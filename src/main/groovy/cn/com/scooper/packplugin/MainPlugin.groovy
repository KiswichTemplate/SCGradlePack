package cn.com.scooper.packplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Compression
import org.gradle.api.tasks.bundling.Tar
import org.gradle.api.tasks.bundling.Zip
import org.gradle.internal.impldep.org.apache.commons.io.FilenameUtils

class MainPlugin implements Plugin<Project>{

    static void createVersion(project, path, time){
        def file = project.file(path)
        file.createNewFile()
        file.text = ("${project.rootProject.name}_r${project.version}_${time}")
    }

    @Override
    void apply(Project project) {
        def extension = project.extensions.create('scooperPack', ScooperPackExtension)

        project.task('prebuild'){

            project.gradle.projectsEvaluated {

                dependsOn  project.bootWar
                mustRunAfter project.bootWar


                project.delete(extension.tempDir)
                project.mkdir(extension.tempDir)
                extension.configFiles.each {file->
                    project.copy{
                        from(file)
                        into("${extension.tempDir}\\config\\${project.rootProject.name}")
                    }
                }

                extension.others.each {file->
                    def baseName = FilenameUtils.getBaseName(file)
                    def path = FilenameUtils.getPath(file)
                    from(path)
                    include(baseName)
                    into("${extension.tempDir}")

                }

                project.copy {
                    from(extension.sqlDir)
                    into("${extension.tempDir}\\sql")
                    include("*.sql")
                }

                project.copy {
                    from(extension.docDir)
                    into(extension.tempDir)
                    include("*")
                }

                if (project.file("build\\libs\\${project.rootProject.name}-${project.version}.war").exists()) {
                    project.copy {
                        from project.zipTree("build\\libs\\${project.rootProject.name}-${project.version}.war")
                        into("${extension.tempDir}\\${project.rootProject.name}")
                    }
                    createVersion(project,"${extension.tempDir}\\${project.rootProject.name}\\version.txt", extension.time)
                }

                createVersion(project,"${extension.tempDir}\\version.txt", extension.time)

                doLast {


                }
            }


        }


        project.task("repackToWar",type: Zip){
            dependsOn project.prebuild

            project.gradle.projectsEvaluated {
                from "${extension.tempDir}\\${project.rootProject.name}"
                into("")
                destinationDir project.file("${extension.tempDir}")
                baseName("${project.rootProject.name}.war")
                archiveName("${project.rootProject.name}.war")

                doLast {
                    project.delete("${extension.tempDir}\\${project.rootProject.name}")
                }
            }


        }

        project.task("projectPack",type: Tar) {
            dependsOn project.repackToWar
            group "scooper"

            project.gradle.projectsEvaluated {
                compression = Compression.GZIP

                from "${extension.tempDir}"
                into("")
                destinationDir project.file("${extension.outDir}\\${project.rootProject.name}\\publish\\${project.version}")
                archiveName("${project.rootProject.name}_r${project.version}_${extension.time}.tgz")

                doLast {
                    project.delete("${extension.tempDir}")
                }
            }

        }
    }
}
