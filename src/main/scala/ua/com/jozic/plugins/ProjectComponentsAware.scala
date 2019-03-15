package ua.com.jozic.plugins

import com.intellij.openapi.project.Project
import reflect.ClassTag

trait ProjectComponentsAware {

  final def projectComponent[Component](project: Project)(implicit m: ClassTag[Component]) =
    project.getComponent(m.runtimeClass.asInstanceOf[Class[Component]])
}