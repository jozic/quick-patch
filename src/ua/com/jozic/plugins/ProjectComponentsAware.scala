package ua.com.jozic.plugins

import com.intellij.openapi.project.Project

trait ProjectComponentsAware {

  final def projectComponent[Component](project: Project)(implicit m: ClassManifest[Component]) =
    project.getComponent(m.erasure.asInstanceOf[Class[Component]])
}