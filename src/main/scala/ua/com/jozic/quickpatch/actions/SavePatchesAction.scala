package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import ua.com.jozic.plugins._
import ua.com.jozic.quickpatch.QuickPatchMessageBundle.message
import ua.com.jozic.quickpatch.components.QuickPatcherComponent

class SavePatchesAction extends BasePatchesAction {

  def doAction(event: AnActionEvent): Unit = {
    val currentProject = event.getProject
    currentProject.projectComponent[QuickPatcherComponent].makePatches()
    doNotify(savedSuccessfully, currentProject)
  }

  val savedSuccessfully = success(
    message("notifications.group.id"),
    message("save.notification.title"),
    message("save.notification.content", "dir")
  )

  override def update(event: AnActionEvent) {
    event.getPresentation.setEnabled(actionEnabled(event))
  }

  def actionEnabled(event: AnActionEvent): Boolean = {
    Option(event.getProject).exists {
      p => ProjectChangeListsManager(p).changeLists.exists(p.projectComponent[QuickPatcherComponent].needToSave)
    }
  }
}