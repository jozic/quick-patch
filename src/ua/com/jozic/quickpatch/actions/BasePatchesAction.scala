package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.{AnActionEvent, AnAction}
import swing.Dialog
import ua.com.jozic.quickpatch.QuickPatchMessageBundle.message
import ua.com.jozic.plugins.{ProjectComponentsAware, ProjectAwareAction, ApplicationComponentsAware}
import ua.com.jozic.quickpatch.components.QuickPatchSettingsComponent

/**
 * @author jozic
 * @since 2/28/11
 */
trait BasePatchesAction extends AnAction with ApplicationComponentsAware with ProjectAwareAction
with ProjectComponentsAware {
  def actionPerformed(event: AnActionEvent) {
    if (patchConfigurationComponent.settings notReady) {
      Dialog.showMessage(null, message("empty.location.error.message.action"),
        message("dialog.title"), Dialog.Message.Error)
    }
    else {
      doAction(event)
    }
  }

  def doAction(event: AnActionEvent): Unit

  def patchConfigurationComponent = applicationComponent[QuickPatchSettingsComponent]
}