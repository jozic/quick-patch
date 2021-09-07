package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent}
import ua.com.jozic.plugins._
import ua.com.jozic.quickpatch.QuickPatchMessageBundle.message
import ua.com.jozic.quickpatch.services.QuickPatchSettings

trait BasePatchesAction extends AnAction with Notifications {

  val NotReady = failure(
    message("notifications.group.id"),
    message("dialog.title"),
    message("empty.location.error.message.action")
  )

  def actionPerformed(event: AnActionEvent): Unit = {
    if (QuickPatchSettings(event.getProject).notReady)
      doNotify(NotReady, event.getProject)
    else
      doAction(event)
  }

  def doAction(event: AnActionEvent): Unit

}