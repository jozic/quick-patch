package ua.com.jozic.plugins

import com.intellij.openapi.application.ApplicationManager

trait ApplicationComponentsAware {

  final def applicationComponent[Component](implicit m: ClassManifest[Component]) =
    ApplicationManager.getApplication.getComponent(m.erasure.asInstanceOf[Class[Component]])
}