package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import ua.com.jozic.quickpatch.components.QuickPatchApplierComponent
import com.intellij.openapi.project.Project

/**
 * @author jozic
 * @since 2/28/11
 */
class RestorePatchesAction extends BasePatchesAction {
  def doAction(event: AnActionEvent) {
    val currentProject = project(event)
    patchApplierComponent(currentProject).restorePatches()
  }

  def patchApplierComponent(project: Project) = projectComponent[QuickPatchApplierComponent](project)
}