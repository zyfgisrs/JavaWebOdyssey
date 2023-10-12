import org.gradle.api.Plugin
import org.gradle.api.Project


class MyPlugin2 implements Plugin<Project>{
    @Override
    void apply(Project project) {
        project.task('myPluginTask2'){
            doLast {
                println('myPluginTask2 success!!!')
            }
        }
    }
}
