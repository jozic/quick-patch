package ua.com.jozic.plugins

import com.intellij.openapi.application.ApplicationManager
import reflect.ClassTag

trait ApplicationComponentsAware {

  final def applicationComponent[Component](implicit m: ClassTag[Component]) =
    ApplicationManager.getApplication.getComponent(m.runtimeClass.asInstanceOf[Class[Component]])
}