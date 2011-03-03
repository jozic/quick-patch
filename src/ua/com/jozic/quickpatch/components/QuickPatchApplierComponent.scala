package ua.com.jozic.quickpatch.components

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.ChangeListManager


class QuickPatchApplierComponent(val project: Project) extends BaseQuickPatchComponent {
  def getComponentName = "QuickPatchApplierComponent"

  def restorePatches() {
    val changeListManager = ChangeListManager.getInstance(project)
    // todo  restore stub
  }
}