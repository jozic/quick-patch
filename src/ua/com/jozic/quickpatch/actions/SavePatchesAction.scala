package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.{AnActionEvent, AnAction}
import ua.com.jozic.quickpatch.components.QuickPatchSettingsComponent
import ua.com.jozic.plugins.{ProjectAwareAction, ApplicationComponentsAware}

class SavePatchesAction extends AnAction with ApplicationComponentsAware with ProjectAwareAction {
  def actionPerformed(event: AnActionEvent) {
    val currentProject = project(event)
    patchConfigurationComponent.savePatchesFor(currentProject)
  }

  def patchConfigurationComponent = applicationComponent[QuickPatchSettingsComponent]
}