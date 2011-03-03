package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import ua.com.jozic.quickpatch.components.QuickPatcherComponent
import com.intellij.openapi.project.Project

class SavePatchesAction extends BasePatchesAction {

  def doAction(event: AnActionEvent) {
    val currentProject = project(event)
    patcherComponent(currentProject).makePatches()
  }

  def patcherComponent(project: Project) = projectComponent[QuickPatcherComponent](project)
}