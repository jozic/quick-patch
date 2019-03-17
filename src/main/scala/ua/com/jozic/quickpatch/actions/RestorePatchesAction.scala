package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import ua.com.jozic.plugins._
import ua.com.jozic.quickpatch.components.QuickPatchApplierComponent


class RestorePatchesAction extends BasePatchesAction {
  def doAction(event: AnActionEvent): Unit = {
    event.getProject.projectComponent[QuickPatchApplierComponent].restorePatches()
  }
}