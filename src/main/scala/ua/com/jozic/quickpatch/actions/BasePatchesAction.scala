package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.{AnActionEvent, AnAction}
import ua.com.jozic.quickpatch.QuickPatchMessageBundle.message
import ua.com.jozic.quickpatch.components.QuickPatchSettingsComponent
import ua.com.jozic.plugins.{Notifications, ProjectComponentsAware, ProjectAwareAction, ApplicationComponentsAware}

/**
 * @author jozic
 * @since 2/28/11
 */
trait BasePatchesAction extends AnAction with ApplicationComponentsAware with ProjectAwareAction
with ProjectComponentsAware with Notifications {
  def actionPerformed(event: AnActionEvent) {
    if (patchConfigurationComponent.settings.notReady) doNotify(notReady, project(event))
    else doAction(event)
  }

  def doAction(event: AnActionEvent)

  def patchConfigurationComponent = applicationComponent[QuickPatchSettingsComponent]

  val notReady = failure(message("notifications.group.id"), message("dialog.title"),
    message("empty.location.error.message.action"))
}