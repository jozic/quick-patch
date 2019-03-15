package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import ua.com.jozic.quickpatch.QuickPatchMessageBundle.message
import ua.com.jozic.quickpatch.components.{SettingsAware, QuickPatcherComponent}
import ua.com.jozic.plugins.ProjectChangeListsManager

class SavePatchesAction extends BasePatchesAction with SettingsAware {

  def doAction(event: AnActionEvent) {
    val currentProject = project(event)
    projectComponent[QuickPatcherComponent](currentProject).makePatches()
    doNotify(savedSuccessfully, currentProject)
  }

  val savedSuccessfully = success(message("notifications.group.id"), message("save.notification.title"),
    message("save.notification.content", "dir"))

  override def update(event: AnActionEvent) {
    event.getPresentation.setEnabled(actionEnabled(event))
  }

  def actionEnabled(event: AnActionEvent) = projectOpt(event) map {
    p => ProjectChangeListsManager(p).changeLists.exists(projectComponent[QuickPatcherComponent](p).needToSave)
  } getOrElse false
}