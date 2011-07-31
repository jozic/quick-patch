package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import ua.com.jozic.quickpatch.QuickPatchMessageBundle.message
import ua.com.jozic.quickpatch.components.{SettingsAware, QuickPatcherComponent}
import ua.com.jozic.plugins.ProjectChangeListsManager

class SavePatchesAction extends BasePatchesAction with SettingsAware {

  def doAction(event: AnActionEvent) {
    val currentProject = project(event)
    patcherComponent(currentProject).makePatches()
    doNotify(savedSuccessfully, currentProject)
  }

  def patcherComponent(project: Project) = projectComponent[QuickPatcherComponent](project)

  val savedSuccessfully = success(message("notifications.group.id"), message("save.notification.title"),
    message("save.notification.content", "dir"))

  override def update(event: AnActionEvent) {
    val presentation = event.getPresentation
    presentation.setEnabled(actionEnabled(event))
  }

  def actionEnabled(event: AnActionEvent) = {
    projectOpt(event) match {
      case Some(p) => {
        val changeListsManager = ProjectChangeListsManager(p)
        val hasNoChanges = changeListsManager.hasNoChangeLists
        val onlyDefaultList = !settings.saveDefault && changeListsManager.hasOnlyDefaultChangeList
        val onlyEmptyLists = !settings.saveEmpty &&
                (if (settings.saveDefault) {
                  changeListsManager.hasOnlyEmptyChangeLists
                } else {
                  changeListsManager.hasOnlyEmptyChangeListsExceptDefault
                })
        !(hasNoChanges || onlyDefaultList || onlyEmptyLists)
      }
      case _ => false
    }
  }
}