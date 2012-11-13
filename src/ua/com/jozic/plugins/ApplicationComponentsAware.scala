package ua.com.jozic.plugins

import com.intellij.openapi.application.ApplicationManager

trait ApplicationComponentsAware {

  def applicationComponent[Component](implicit m: scala.reflect.ClassManifest[Component]) =
    ApplicationManager.getApplication.getComponent(m.erasure.asInstanceOf[Class[Component]])
}