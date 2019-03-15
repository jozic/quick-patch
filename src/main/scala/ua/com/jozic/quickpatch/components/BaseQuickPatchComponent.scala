package ua.com.jozic.quickpatch.components

import com.intellij.openapi.components.ProjectComponent

trait BaseQuickPatchComponent extends ProjectComponent with SettingsAware {

  def disposeComponent() {}

  def initComponent() {}

  def projectClosed() {}

  def projectOpened() {}
}