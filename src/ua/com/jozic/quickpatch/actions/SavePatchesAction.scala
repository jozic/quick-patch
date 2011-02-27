package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.{AnActionEvent, AnAction}
import ua.com.jozic.quickpatch.components.QuickPatchSettingsComponent
import ua.com.jozic.plugins.{ProjectAwareAction, ApplicationComponentsAware}
import swing.Dialog
import ua.com.jozic.quickpatch.QuickPatchMessageBundle.message

class SavePatchesAction extends AnAction with ApplicationComponentsAware with ProjectAwareAction {
  def actionPerformed(event: AnActionEvent) {
    if (patchConfigurationComponent.settings notReady) {
      Dialog.showMessage(null, message("empty.location.error.message.action"),
        message("dialog.title"), Dialog.Message.Error)
    }
    else {
      val currentProject = project(event)
      patchConfigurationComponent.savePatchesFor(currentProject)
    }
  }

  def patchConfigurationComponent = applicationComponent[QuickPatchSettingsComponent]
}