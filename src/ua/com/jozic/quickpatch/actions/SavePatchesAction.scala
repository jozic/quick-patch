package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import ua.com.jozic.quickpatch.components.QuickPatcherComponent
import com.intellij.openapi.project.Project
import ua.com.jozic.quickpatch.QuickPatchMessageBundle.message

class SavePatchesAction extends BasePatchesAction {

  def doAction(event: AnActionEvent) {
    val currentProject = project(event)
    patcherComponent(currentProject).makePatches()
    doNotify(savedSuccessfully, currentProject)
  }

  def patcherComponent(project: Project) = projectComponent[QuickPatcherComponent](project)

  val savedSuccessfully = success(message("notifications.group.id"), message("save.notification.title"),
    message("save.notification.content", "dir"))
}