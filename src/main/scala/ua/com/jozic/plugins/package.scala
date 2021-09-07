package ua.com.jozic

import scala.reflect.{ClassTag, classTag}

import com.intellij.openapi.project.Project

package object plugins {

  implicit class ProjectOps(val project: Project) extends AnyVal {
    def get[Component: ClassTag]: Component =
      project.getService(classTag[Component].runtimeClass.asInstanceOf[Class[Component]])
  }

}
