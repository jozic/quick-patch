package ua.com.jozic.quickpatch.components

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project

class QuickPatchApplierComponent(val project: Project) extends ProjectComponent {
  override def getComponentName = "QuickPatchApplierComponent"

  def restorePatches() {
    // todo  restore stub
  }
}