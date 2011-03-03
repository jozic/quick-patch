package ua.com.jozic.quickpatch.components

import ua.com.jozic.plugins.ApplicationComponentsAware
import com.intellij.openapi.components.ProjectComponent


trait BaseQuickPatchComponent extends ProjectComponent with ApplicationComponentsAware {

  def settings = applicationComponent[QuickPatchSettingsComponent].settings

  def disposeComponent() {}

  def initComponent() {}

  def projectClosed() {}

  def projectOpened() {}
}