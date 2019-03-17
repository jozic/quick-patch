package ua.com.jozic

import com.intellij.openapi.project.Project

import scala.reflect.{ClassTag, classTag}

package object plugins {

  implicit class ProjectOps(val project: Project) extends AnyVal {
    def projectComponent[Component: ClassTag]: Component =
      project.getComponent(classTag[Component].runtimeClass.asInstanceOf[Class[Component]])
  }

}
