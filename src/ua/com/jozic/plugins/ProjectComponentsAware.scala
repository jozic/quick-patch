package ua.com.jozic.plugins

import com.intellij.openapi.project.Project

trait ProjectComponentsAware {

  def projectComponent[Component](project: Project)(implicit m: scala.reflect.Manifest[Component]): Component =
    project.getComponent(m.erasure.asInstanceOf[Class[Component]])
}